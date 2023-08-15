    package br.unitins.topicos1.service;

    import br.unitins.topicos1.api.dto.request.FinalizarCompraRequest;
    import br.unitins.topicos1.api.dto.request.PreCompraDto;
    import br.unitins.topicos1.api.dto.response.CompraResponse;
    import br.unitins.topicos1.api.exception.BadRequestException;
    import br.unitins.topicos1.api.exception.ForbiddenException;
    import br.unitins.topicos1.api.mapper.CompraMapper;
    import br.unitins.topicos1.api.mapper.PagamentoMapper;
    import br.unitins.topicos1.api.util.RequestValidator;
    import br.unitins.topicos1.domain.enums.StatusAnuncio;
    import br.unitins.topicos1.domain.enums.StatusPagamento;
    import br.unitins.topicos1.domain.exception.CompraNaoEncontradoException;
    import br.unitins.topicos1.domain.exception.VariacaoConflitoException;
    import br.unitins.topicos1.domain.model.*;
    import jakarta.enterprise.context.ApplicationScoped;
    import jakarta.inject.Inject;
    import jakarta.transaction.Transactional;
    import org.eclipse.microprofile.jwt.JsonWebToken;

    import java.util.*;
    import java.util.stream.Collectors;

    import static br.unitins.topicos1.domain.enums.FormaPagamento.CARTAO_CREDITO;

    @ApplicationScoped
    public class PreCompraServiceImpl implements PreCompraService {
        @Inject
        VariacaoService variacaoService;
        @Inject
        AnuncioService anuncioService;
        @Inject
        UsuarioService usuarioService;
        @Inject
        CompraMapper compraMapper;

        @Inject
        JsonWebToken jwt;
        @Inject
        RequestValidator validator;

        @Override
        public Compra buscarOuFalharEntidadePorId(Long id) {
            Compra preCompra = Compra.buscarPorId(id)
                    .orElseThrow(() -> new CompraNaoEncontradoException(String.format("Compra %s não encontrada.", id)));
            permissaoPreCompra(preCompra);
            return preCompra;
        }

        @Override
        public CompraResponse buscarPorId(Long id) {
            return compraMapper.toResponse(buscarOuFalharEntidadePorId(id));
        }

        @Override
        public List<CompraResponse> buscarPreCompraPessoais() {
            return Compra.buscarPreCompraPessoais(
                            usuarioService.buscarPorLogin(jwt.getSubject())).stream()
                    .map(compraMapper::toResponse)
                    .collect(Collectors.toList());
        }


        private List<Variacao> buscarVariacoes(List<Long> variacoesId) {
            return variacoesId.stream()
                    .map(id -> variacaoService.buscarOuFalharEntidadePorId(id))
                    .toList();
        }


        private List<Variacao> filtrarVariacoesSelecionadas(List<Variacao> variacoes, Compra preCompra) {
            return variacoes.stream()
                    .filter(variacao -> preCompra.variacoes.contains(variacao))
                    .toList();
        }

        @Transactional
        @Override
        public void removerProdutos(PreCompraDto dto) {
            Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());
            List<Variacao> variacoes = buscarVariacoes(dto.variacoes());
            List<Anuncio> anuncios = anuncioService.buscarListaPorVariacoes(dto.variacoes());
            validarPreCompra(anuncios, usuario);

            for (Anuncio a : anuncios) {
                Compra preCompra = Compra.buscarCompraPorAnuncio(a, usuario)
                        .orElseThrow(() -> new CompraNaoEncontradoException("Compra não encontrada"));
                permissaoPreCompra(preCompra);
                List<Variacao> variacoesSelecionada = filtrarVariacoesSelecionadas(variacoes, preCompra);

                preCompra.variacoes.removeAll(variacoesSelecionada);

                if (preCompra.variacoes.isEmpty()) {
                    preCompra.delete();
                } else {
                    preCompra.reduzirQuantidade(variacoesSelecionada.size());
                    preCompra.reduzirValor(valorCompra(variacoesSelecionada));
                }
            }
        }

        @Override
        @Transactional
        public void adicionarProdutos(PreCompraDto dto) {
            Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());

            validator.validate(dto);

            List<Anuncio> anuncios = anuncioService.buscarListaPorVariacoes(dto.variacoes());

            validarPreCompra(anuncios, usuario);
            List<Variacao> variacoes = dto.variacoes().stream()
                    .map(v -> variacaoService.buscarOuFalharEntidadePorId(v)).toList();

            for (Anuncio a : anuncios) {
                Compra preCompra = Compra.buscarCompraPorAnuncio(a, usuario)
                        .orElse(new Compra());

                List<Variacao> variacoesDoAnuncio = variacoes.stream()
                        .filter(variacao -> a.variacoes.contains(variacao))
                        .toList();

                persistirOuAdicionarProdutosACompraExistente(usuario, a, preCompra, variacoesDoAnuncio);
            }

        }

        private void persistirOuAdicionarProdutosACompraExistente(Usuario usuario, Anuncio anuncio, Compra preCompra, List<Variacao> variacoesValidadas) {
            if (!preCompra.isPersistent()) {
                preCompra.comprador = usuario;
                preCompra.anuncio = anuncio;
                preCompra.variacoes = variacoesValidadas;
                preCompra.quantidade = variacoesValidadas.size();
                preCompra.valor = valorCompra(variacoesValidadas);
                preCompra.persist();
            } else {
                permissaoPreCompra(preCompra);
                preCompra.variacoes.addAll(variacoesValidadas);
                preCompra.quantidade = preCompra.quantidade + variacoesValidadas.size();
                preCompra.valor = preCompra.valor + valorCompra(variacoesValidadas);
            }

            verificarEstoque(preCompra.anuncio, preCompra.variacoes);
        }


        private void verificarEstoque(Anuncio anuncio, List<Variacao> variacoes) {

            List<Variacao> listaVariacoesDaPaginaProduto = Variacao.buscarVariacoes(anuncio.id);

            List<Variacao> variacoesCompradas = variacoes.stream()
                    .map(v -> listaVariacoesDaPaginaProduto.stream()
                            .filter(variacao -> variacao.id.equals(v.id))
                            .findFirst()
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .filter(variacao -> variacao.quantidadeEstoque >= 1)
                    .toList();

            if (variacoesCompradas.isEmpty() || listaVariacoesDaPaginaProduto.size() < variacoes.size()) {
                throw new VariacaoConflitoException("Produto sem estoque.");
            }
        }


        private double valorCompra(List<Variacao> variacoesValidadas) {
            return variacoesValidadas.stream()
                    .mapToDouble(value -> value.valorBruto)
                    .sum();
        }

        @Override
        public void deletarPreCompra(Long id) {
            Compra preCompra = buscarOuFalharEntidadePorId(id);
            permissaoPreCompra(preCompra);
            preCompra.delete();
        }


        private void permissaoPreCompra(Compra preCompra) {
            if (preCompra.permissaoCompra(usuarioService.buscarPorLogin(jwt.getSubject()))) {
                throw new ForbiddenException("Usuário sem permissão para alterar essa lista de compras.");
            }
        }

        private void validarPreCompra(List<Anuncio> anuncios, Usuario usuario) {
            anuncios.forEach(anuncio -> {
                if (usuario == anuncio.usuario) {
                    throw new BadRequestException(String.format("Perfil %s incorreto.", usuario.nome));
                }

                if (anuncio.statusAnuncio != StatusAnuncio.ATIVO) {
                    throw new BadRequestException(String.format("Anuncio %s não está ativo.", anuncio.titulo));
                }
            });
        }
    }

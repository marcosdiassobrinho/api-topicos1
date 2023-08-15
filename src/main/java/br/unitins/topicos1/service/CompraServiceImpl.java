package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.CompraRequest;
import br.unitins.topicos1.api.dto.request.FinalizarCompraRequest;
import br.unitins.topicos1.api.dto.request.PagamentoRequest;
import br.unitins.topicos1.api.dto.response.CompraResponse;
import br.unitins.topicos1.api.exception.ForbiddenException;
import br.unitins.topicos1.api.mapper.CompraMapper;
import br.unitins.topicos1.api.mapper.PagamentoMapper;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.enums.StatusPagamento;
import br.unitins.topicos1.domain.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.unitins.topicos1.domain.enums.FormaPagamento.CARTAO_CREDITO;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {
    @Inject
    UsuarioService usuarioService;

    @Inject
    CompraMapper compraMapper;

    @Inject
    VariacaoService variacaoService;

    @Inject
    PagamentoMapper pagamentoMapper;
    @Inject
    ParcelaService parcelaService;

    @Inject
    RequestValidator validator;

    @Inject
    JsonWebToken jwt;


    @Transactional
    public void finalizarCompra(CompraRequest request, String login) {
        validator.validate(request);
        Compra compra = compraMapper.toEntity(request);
        definirEndereco(compra, login);
        Pagamento pagamento = criarPagamento(request.pagamentoRequest(), login, compra);
        processarPagamento(pagamento, valorTotalCompras(pagamento));
    }

    @Override
    public List<CompraResponse> buscarComprasPessoais(String login) {
        Usuario usuario = usuarioService.buscarPorLogin(login);
        List<Compra> compras = Compra.buscarComprasPessoais(usuario);

        return compraMapper.toListResponse(compras);
    }

    @Override
    public List<CompraResponse> buscarVendasPessoais(String login) {
        Usuario usuario = usuarioService.buscarPorLogin(login);
        List<Compra> compras = Compra.buscarVendasPessoais(usuario);

        return compraMapper.toListResponse(compras);
    }


    private void definirEndereco(Compra compra, String login) {
        Usuario usuario = usuarioService.buscarPorLogin(login);
        if (compra.endereco == null) {
            compra.endereco = usuario.getEnderecoPrincipal();
        } else {
            compra.endereco = Endereco.findById(compra.endereco.id);
        }
    }

    private double valorTotalCompras(Pagamento pagamento) {
        return pagamento.compra.stream().
                mapToDouble(value -> value.valor)
                .sum();
    }


    private Pagamento criarPagamento(PagamentoRequest dto, String login, Compra compra) {
        Pagamento pagamento = pagamentoMapper.toEntity(dto);
        pagamento.compra = new ArrayList<>();
        List<Compra> preCompras = buscarPreComprasPessoais(login);

        List<Variacao> variacoes = buscarVariacoes(compra.variacoes);

        preCompras.forEach(preCompra -> {
            processarPreCompra(pagamento, preCompra, variacoes, compra.endereco);
        });

        pagamento.statusPagamento = StatusPagamento.EM_ANALISE;
        pagamento.persist();


        return pagamento;
    }

    private List<Variacao> buscarVariacoes(List<Variacao> variacoes) {
        return variacoes.stream()
                .map(variacao -> variacaoService.buscarOuFalharEntidadePorId(variacao.id))
                .toList();
    }

    private void processarPagamento(Pagamento pagamento, double valorCompras) {
        if (pagamento.formaPagamento.equals(CARTAO_CREDITO)) {
            if (pagamento.quantParcelas > 1) {
                pagamento.valorTotal = parcelaService.criarParcelas(pagamento.quantParcelas, pagamento, valorCompras);
            }
        } else {
            pagamento.valorTotal = valorCompras;
        }
    }

    private List<Compra> buscarPreComprasPessoais(String login) {
        return Compra.buscarPreCompraPessoais(usuarioService.buscarPorLogin(login));
    }


    private void processarPreCompra(Pagamento pagamento, Compra preCompra, List<Variacao> variacoes, Endereco endereco) {
        permissaoPreCompra(preCompra);

        List<Variacao> variacoesSelecionadas = filtrarVariacoesSelecionadas(variacoes, preCompra);

        preCompra.variacoes.removeAll(variacoesSelecionadas);
        List<Variacao> variacaoPedidos = converterParaPedidos(variacoesSelecionadas);

        gerarNovaCompra(pagamento, preCompra, variacaoPedidos, endereco);
        variacaoService.atualizarEstoqueAnuncio(preCompra.anuncio, variacoesSelecionadas);
    }

    private List<Variacao> filtrarVariacoesSelecionadas(List<Variacao> variacoes, Compra preCompra) {
        Map<Variacao, Long> preCompraVariacaoCounts = preCompra.variacoes.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return variacoes.stream()
                .filter(variacao -> preCompraVariacaoCounts.merge(variacao, -1L, Long::sum) >= 0)
                .collect(Collectors.toList());
    }


    private List<Variacao> converterParaPedidos(List<Variacao> variacoesSelecionadas) {
        List<Variacao> variacaoPedidos = variacoesSelecionadas.stream()
                .map(Variacao::new)
                .toList();
        variacaoPedidos.forEach(variacao -> variacao.persist());

        return variacaoPedidos;
    }

    private void gerarNovaCompra(Pagamento pagamento, Compra preCompra, List<Variacao> variacaoPedidos, Endereco endereco) {
        double valorCompra = valorCompra(variacaoPedidos);
        Compra compra;
        if (preCompra.variacoes.isEmpty()) {
            compra = preCompra;
            compra.atualizarCompra(variacaoPedidos, valorCompra, endereco);
        } else {
            compra = new Compra(preCompra, variacaoPedidos, valorCompra);
        }
        compra.pagamento = pagamento;
        compra.persistAndFlush();
        pagamento.compra.add(compra);
    }

    private double valorCompra(List<Variacao> variacoesValidadas) {
        return variacoesValidadas.stream()
                .mapToDouble(value -> value.valorBruto)
                .sum();
    }

    private void permissaoPreCompra(Compra preCompra) {
        if (preCompra.permissaoCompra(usuarioService.buscarPorLogin(jwt.getSubject()))) {
            throw new ForbiddenException("Usuário sem permissão para alterar essa lista de compras.");
        }
    }

}

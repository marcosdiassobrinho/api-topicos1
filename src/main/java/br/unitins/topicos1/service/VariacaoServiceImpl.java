package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.VariacaoDto;
import br.unitins.topicos1.api.dto.response.VariacaoResponseDto;
import br.unitins.topicos1.api.exception.ForbiddenException;
import br.unitins.topicos1.api.mapper.VariacaoMapper;
import br.unitins.topicos1.api.util.BeanUtil;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.exception.VariacaoConflitoException;
import br.unitins.topicos1.domain.exception.VariacaoNaoEncontradoException;
import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.domain.model.Variacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class VariacaoServiceImpl implements VariacaoService {

    @Inject
    AnuncioService anuncioService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    RequestValidator requestValidator;

    @Inject
    VariacaoMapper variacaoMapper;


    @Inject
    JsonWebToken jwt;

    @Override
    public List<VariacaoResponseDto> buscarTodos(Long anuncioId) {
        return variacaoMapper.toListDto(Variacao.buscarVariacoes(anuncioId));
    }

    @Override
    public VariacaoResponseDto buscarPorId(Long id) {
        return variacaoMapper
                .toDto(buscarOuFalharEntidadePorId(id));
    }

    @Override
    public Variacao buscarOuFalharEntidadePorId(Long id) {
        return Variacao.buscarPorId(id)
                .orElseThrow(() -> new VariacaoNaoEncontradoException(String.format("Variacão de id %d não encontrada.", id)));
    }

    @Override
    @Transactional
    public void salvar(Long idAnuncio, VariacaoDto dto) {
        requestValidator.validate(dto);

        Anuncio anuncio = anuncioService.buscarOuFalharEntidadePorId(idAnuncio);
        permissaoAnuncio(anuncio);

        Variacao variacao = variacaoMapper.toEntity(dto);

        Variacao.persist(variacao);

        anuncio.variacoes.add(variacao);

    }

    @Override
    @Transactional
    public void atualizar(Long anuncioId, Long idVariacao, VariacaoDto dto) {
        Anuncio anuncio = anuncioService.buscarOuFalharEntidadePorId(anuncioId);
        permissaoAnuncio(anuncio);

        requestValidator.validateNonNullProperties(dto);

        Variacao target = buscarOuFalharEntidadePorId(idVariacao);

        if (!anuncio.variacoes.contains(target)) {
            throw new VariacaoConflitoException(String.format("A variação %s não pertence ao anuncio %s", target.descricao, anuncio.titulo));
        }

        Variacao source = variacaoMapper.toEntity(dto);
        BeanUtil.copyNonNullProperties(source, target);

    }

    @Override
    @Transactional
    public void atualizarVariacaoImagem(Long anuncioId, String nomeImagem, Long variacaoId) {
        Anuncio anuncio = anuncioService.buscarOuFalharEntidadePorId(anuncioId);
        permissaoAnuncio(anuncio);

        Variacao variacao = buscarOuFalharEntidadePorId(variacaoId);
        variacao.nomeImagem = nomeImagem;
    }

    @Override
    @Transactional
    public void deletar(Long anuncioId, Long varicaoId) {
        Anuncio anuncio = anuncioService.buscarOuFalharEntidadePorId(anuncioId);
        permissaoAnuncio(anuncio);

        Variacao variacao = buscarOuFalharEntidadePorId(varicaoId);

        if (anuncio.variacoes.size() == 1) {
            throw new VariacaoConflitoException(String.format("A variação %s não pode ser deletada, o anuncio %s deve possuir ao menos uma variação.", variacao.descricao, anuncio.titulo));
        }

        variacaoPertenceAoAnuncio(anuncio, variacao);

        anuncio.variacoes.remove(variacao);

        variacao.delete();
    }

    private void variacaoPertenceAoAnuncio(Anuncio anuncio, Variacao variacao) {
        if (!anuncio.variacoes.contains(variacao)) {
            throw new VariacaoConflitoException(String.format("A variação %s não pertence ao anuncio %s", variacao.descricao, anuncio.titulo));
        }
    }

    @Transactional
    @Override
    public List<Variacao> atualizarEstoqueAnuncio(Anuncio anuncio, List<Variacao> variacoes) {

        List<Variacao> listaVariacoesDaPaginaProduto = Variacao.buscarVariacoes(anuncio.id);

        List<Variacao> variacoesCompradas = variacoes.stream()
                .map(v -> listaVariacoesDaPaginaProduto.stream()
                        .filter(variacao -> variacao.id.equals(v.id))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .filter(variacao -> variacao.quantidadeEstoque >= 1)
                .peek(variacao -> variacao.quantidadeEstoque -= 1)
                .collect(Collectors.toList());

        if (variacoesCompradas.isEmpty()) {
            throw new VariacaoConflitoException("Produto sem estoque.");
        }

        return variacoesCompradas;
    }


    private Double deduzirTaxa(Double valor) {
        return valor * 0.95;
    }

    private void permissaoAnuncio(Anuncio anuncio) {
        if (anuncio.permissaoAnuncio(usuarioService.buscarPorLogin(jwt.getSubject()))) {
            throw new ForbiddenException("Usuário sem permissão para alterar a variação.");
        }
    }

    @Transactional
    public void persistir(Variacao variacao) {
        variacao.valorLiquido = deduzirTaxa(variacao.valorBruto);
        variacao.persistAndFlush();
    }
}

package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.AnuncioDto;
import br.unitins.topicos1.api.dto.request.VariacaoDto;
import br.unitins.topicos1.api.mapper.AnuncioMapper;
import br.unitins.topicos1.api.mapper.VariacaoMapper;
import br.unitins.topicos1.api.util.BeanUtil;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.enums.StatusAnuncio;
import br.unitins.topicos1.domain.exception.AnuncioConflitoException;
import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.domain.model.enums.StatusUsuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@ApplicationScoped
public class AnunciarService {

    @Inject
    UsuarioService usuarioService;

    @Inject
    AnuncioService anuncioService;

    @Inject
    JsonWebToken jwt;
    @Inject
    RequestValidator requestValidator;
    @Inject
    ProdutoService produtoService;
    @Inject
    AnuncioMapper anuncioMapper;
    @Inject
    VariacaoMapper variacaoMapper;
    @Inject
    VariacaoService variacaoService;

    @Transactional
    public void iniciarAnuncio(Long produtoId) {
        Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());

        falhaCasoUsuarioIncompleto(usuario);

        Anuncio anuncio = Anuncio.buscarAnuncioExistente(usuario)
                .orElse(new Anuncio(usuario));

        if (!anuncio.isPersistent()) {
            anuncio.persistAndFlush();
        }

        anuncio.produto = produtoService.buscarOuFalharEntidadePorId(produtoId);
        anuncio.statusAnuncio = StatusAnuncio.INCOMPLETO;
    }

    @Transactional
    public void configurarAnuncio(AnuncioDto dto) {
        Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());

        requestValidator.validate(dto);
        Anuncio target = anuncioService.buscarOuFalharAnuncioIncompleto(usuario);

        Anuncio source = anuncioMapper.toEntity(dto);

        BeanUtil.copyNonNullProperties(source, target);

        target.statusAnuncio = StatusAnuncio.COMPLEMENTAR;
    }

    @Transactional
    public void complementarVariacoesAnuncio(List<VariacaoDto> dto) {
        Usuario usuario = usuarioService.buscarPorLogin(jwt.getSubject());

        dto.forEach(request -> requestValidator.validate(request));
        Anuncio anuncio = anuncioService.buscarOuFalharAnuncioComplementar(usuario);

        List<Variacao> variacoes = variacaoMapper.listToEntity(dto);

        variacoes.forEach(variacaoService::persistir);
        anuncio.variacoes = variacoes;
        anuncio.statusAnuncio = StatusAnuncio.ANALISE;
    }


    private void falhaCasoUsuarioIncompleto(Usuario usuario) {
        if (!usuario.statusUsuario.equals(StatusUsuario.COMPLETO)) {
            throw new AnuncioConflitoException("Usuario não possui um perfil completo.");
        }
    }

}

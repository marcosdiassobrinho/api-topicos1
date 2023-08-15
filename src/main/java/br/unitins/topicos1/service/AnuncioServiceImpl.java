package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.AnuncioDto;
import br.unitins.topicos1.api.dto.response.AnuncioResponse;
import br.unitins.topicos1.api.exception.ForbiddenException;
import br.unitins.topicos1.api.mapper.AnuncioMapper;
import br.unitins.topicos1.api.util.BeanUtil;
import br.unitins.topicos1.api.util.RequestValidator;
import br.unitins.topicos1.domain.exception.AnuncioNaoEncontradoException;
import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class AnuncioServiceImpl implements AnuncioService {

    @Inject
    UsuarioService usuarioService;
    @Inject
    JsonWebToken jwt;
    @Inject
    RequestValidator requestValidator;

    @Inject
    AnuncioMapper anuncioMapper;

    public List<AnuncioResponse> buscarTodos() {

        return Anuncio.buscarAnunciosAtivos().stream()
                .map(anuncioMapper::toResponse)
                .collect(Collectors.toList()
                );
    }

    @Override
    public List<AnuncioResponse> buscarAnunciosPessoais() {
        return Anuncio.buscarAnunciosPessoais(usuarioService.buscarPorLogin(jwt.getSubject()))
                .stream()
                .map(anuncioMapper::toResponse)
                .collect(Collectors.toList()
                );
    }

    @Override
    public List<AnuncioResponse> buscarAnunciosEmAnalise() {
        return Anuncio.buscarAnunciosAnalises().stream()
                .map(anuncioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Anuncio buscarAnuncioPorVariacoes(List<Long> variacoes) {
        return Anuncio.buscarPorVariacoes(variacoes)
                .orElseThrow(() -> new AnuncioNaoEncontradoException("Anuncio não foi encontrado.")
                );
    }

    public List<Anuncio> buscarListaPorVariacoes(List<Long> variacoes) {
        return Anuncio.buscarListaPorVariacoes(variacoes);
    }

    public AnuncioResponse buscarOuFalhar(Long id) {
        return anuncioMapper.toResponse(buscarOuFalharEntidadePorId(id));
    }

    @Override
    @Transactional
    public AnuncioResponse atualizar(AnuncioDto dto, Long anuncioId) {
        Anuncio target = buscarOuFalharEntidadePorId(anuncioId);
        permissaoAnuncio(target);

        requestValidator.validateNonNullProperties(dto);
        Anuncio source = anuncioMapper.toEntity(dto);

        BeanUtil.copyNonNullProperties(source, target);
        return buscarOuFalhar(target.id);
    }


    private void permissaoAnuncio(Anuncio anuncio) {
        if (anuncio.permissaoAnuncio(usuarioService.buscarPorLogin(jwt.getSubject()))) {
            throw new ForbiddenException("Usuário sem permissão para alterar o anuncio.");
        }
    }


    @Override
    @Transactional
    public void desativarAnuncio(Long anuncioId) {
        Anuncio anuncio = buscarOuFalharEntidadePorId(anuncioId);
        permissaoAnuncio(anuncio);
        anuncio.desativar();
    }

    @Override
    @Transactional
    public void ativarAnuncio(Long anuncioId) {
        Anuncio anuncio = buscarOuFalharEntidadePorId(anuncioId);
        anuncio.permissaoAnuncio(usuarioService.buscarPorLogin(jwt.getSubject()));
        anuncio.ativar();
    }

    @Override
    public Anuncio buscarOuFalharEntidadePorId(Long id) {
        return Anuncio.buscarPorId(id)
                .orElseThrow(() -> new AnuncioNaoEncontradoException(String.format("Anuncio de id: %s não foi encontrado", id)));
    }

    @Override
    public Anuncio buscarOuFalharAnuncioIncompleto(Usuario usuario) {
        return Anuncio.buscarAnuncioIncompleto(usuario)
                .orElseThrow(() -> new AnuncioNaoEncontradoException(String.format("Anuncio do perfil: %s não foi encontrado", usuario)));
    }


    @Override
    public Anuncio buscarOuFalharAnuncioComplementar(Usuario usuario) {
        return Anuncio.buscarAnuncioComplementar(usuario)
                .orElseThrow(() -> new AnuncioNaoEncontradoException(String.format("Anuncio do perfil: %s não foi encontrado", usuario)));

    }

    @Override
    @Transactional
    public void deletar(Long anuncioId) {
        Anuncio anuncio = buscarOuFalharEntidadePorId(anuncioId);
        anuncio.permissaoAnuncio(usuarioService.buscarPorLogin(jwt.getSubject()));
        anuncio.delete();
    }

}

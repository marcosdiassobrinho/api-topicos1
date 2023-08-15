package br.unitins.topicos1.service;

import br.unitins.topicos1.api.dto.request.AnuncioDto;
import br.unitins.topicos1.api.dto.response.AnuncioResponse;
import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Usuario;

import java.util.List;

public interface AnuncioService {

    void desativarAnuncio(Long anuncioId);
    public void ativarAnuncio(Long anuncioId);

    Anuncio buscarOuFalharEntidadePorId(Long id);

    List<AnuncioResponse> buscarTodos();

    AnuncioResponse buscarOuFalhar(Long id);

    AnuncioResponse atualizar(AnuncioDto dto, Long anuncioId);

    void deletar(Long anuncioId);

    Anuncio buscarOuFalharAnuncioIncompleto(Usuario usuario);

    Anuncio buscarOuFalharAnuncioComplementar(Usuario usuario);

    List<AnuncioResponse> buscarAnunciosPessoais();

    List<AnuncioResponse> buscarAnunciosEmAnalise();

    Anuncio buscarAnuncioPorVariacoes(List<Long> variacoes);

    List<Anuncio> buscarListaPorVariacoes(List<Long> variacoes);
}

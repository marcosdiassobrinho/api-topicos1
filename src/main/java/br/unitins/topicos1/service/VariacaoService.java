package br.unitins.topicos1.service;

import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.api.dto.request.VariacaoDto;
import br.unitins.topicos1.api.dto.response.VariacaoResponseDto;

import java.util.List;

public interface VariacaoService {

    void atualizarVariacaoImagem(Long anuncioId, String nomeImagem, Long variacaoId);

    List<VariacaoResponseDto> buscarTodos(Long anuncioId);

    Variacao buscarOuFalharEntidadePorId(Long id);

    VariacaoResponseDto buscarPorId(Long id);

    void salvar(Long idAnuncio, VariacaoDto dto);

    void atualizar(Long anuncioId, Long idVariacao, VariacaoDto dto);

    void deletar(Long anuncioId, Long varicaoId);

    List<Variacao> atualizarEstoqueAnuncio(Anuncio anuncio, List<Variacao> variacoes);


    void persistir(Variacao variacao);

}

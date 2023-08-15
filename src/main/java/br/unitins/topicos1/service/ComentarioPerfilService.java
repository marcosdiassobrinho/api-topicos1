package br.unitins.topicos1.service;

import br.unitins.topicos1.domain.model.ComentarioPerfil;
import br.unitins.topicos1.api.dto.request.ComentarioPerfilDto;
import br.unitins.topicos1.api.dto.response.ComentarioPerfilResponseDto;

import java.util.List;

public interface ComentarioPerfilService {
    List<ComentarioPerfilResponseDto> buscarComentariosPorPerfil(Long idPerfil);

    public ComentarioPerfilResponseDto denunciarComentario(Long idPerfil, Long idComentario);

    ComentarioPerfilResponseDto buscarPorID(Long perfilId, Long idComentario);

    ComentarioPerfil buscarEntidadePorId(Long perfilId, Long idComentario);

    Long salvar(Long idPerfil, Long IdRemetente, ComentarioPerfilDto dto);

    void deletar(Long idPerfil, Long idComentario);

}

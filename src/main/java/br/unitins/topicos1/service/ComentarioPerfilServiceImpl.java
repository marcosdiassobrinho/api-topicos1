//package br.unitins.topicos1.service;
//
//import br.unitins.topicos1.api.dto.request.ComentarioPerfilDto;
//import br.unitins.topicos1.api.dto.response.ComentarioPerfilResponseDto;
//import br.unitins.topicos1.api.util.RequestValidator;
//import br.unitins.topicos1.domain.exception.ComentarioPerfilNaoEncontradoException;
//import br.unitins.topicos1.domain.exception.PerfilConflitoException;
//import br.unitins.topicos1.domain.model.ComentarioPerfil;
//import br.unitins.topicos1.domain.model.Usuario;
//import br.unitins.topicos1.domain.repository.ComentarioPerfilRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.NoResultException;
//import jakarta.transaction.Transactional;
//
//import java.util.List;
//
//import static java.time.LocalDateTime.now;
//
//@ApplicationScoped
//public class ComentarioPerfilServiceImpl implements ComentarioPerfilService {
//    @Inject
//    ComentarioPerfilRepository comentarioPerfilRepository;
//    @Inject
//    UsuarioService usuarioService;
//    @Inject
//    RequestValidator requestValidator;
//
//    public ComentarioPerfil buscarEntidadePorId(Long idPerfil, Long id) {
//        try {
//            return comentarioPerfilRepository.buscarEntidadePorId(id, idPerfil);
//        } catch (NoResultException e) {
//            throw new ComentarioPerfilNaoEncontradoException(String.format("Comentário %d não encontrado,", id));
//        }
//    }
//
//    public List<ComentarioPerfilResponseDto> buscarComentariosPorPerfil(Long idPerfil) {
//        Usuario usuario = usuarioService.buscarOuFalharEntidadePorId(idPerfil);
//        return comentarioPerfilRepository.buscarComentariosPorPerfil(usuario);
//    }
//
//    public List<ComentarioPerfilResponseDto> buscarTodos() {
//        return comentarioPerfilRepository.buscarTodos();
//    }
//
//    public ComentarioPerfilResponseDto buscarPorID(Long idPerfil, Long idComentario) {
//        try {
//            return comentarioPerfilRepository.buscarPorId(idComentario, idPerfil);
//        } catch (NoResultException e) {
//            throw new ComentarioPerfilNaoEncontradoException(String.format("Comentário %d não encontrado.", idComentario));
//        }
//    }
//
//    @Transactional
//    public Long salvar(Long idPerfil, Long IdRemetente, ComentarioPerfilDto dto) {
//        requestValidator.validate(dto);
//        ComentarioPerfil comentario = new ComentarioPerfil();
//        Perfil destinatario = perfilService.buscarOuFalharPerfilCompleto(idPerfil);
//        Perfil remetente = perfilService.buscarOuFalharPerfilCompleto(IdRemetente);
//
//        if (destinatario.equals(remetente)) {
//            throw new PerfilConflitoException(String.format("Remetente %s possui o mesmo destino: %s. ", remetente.nome, destinatario.nome));
//        }
//
//        if (comentarioPerfilRepository.existeComentario(remetente, destinatario)) {
//            throw new PerfilConflitoException(String.format("O login %s já comentou no perfil de %s.", remetente.nome, destinatario.nome));
//        }
//
//        comentario.remetente = remetente;
//        comentario.destinatario = destinatario;
//        comentario.dataComentario = now();
//        comentario.comentario = dto.comentario();
//        ComentarioPerfil.persist(comentario);
//        return comentario.id;
//    }
//
//    @Transactional
//    public void deletar(Long idPerfil, Long idComentario) {
//        ComentarioPerfil comentario = buscarEntidadePorId(idPerfil, idComentario);
//        if (comentario.destinatario.id.equals(idPerfil) || comentario.remetente.id.equals(idPerfil))
//            comentarioPerfilRepository.delete(comentario);
//    }
//
//    @Transactional
//    public ComentarioPerfilResponseDto denunciarComentario(Long idPerfil, Long idComentario) {
//        ComentarioPerfil comentario = buscarEntidadePorId(idPerfil, idComentario);
//        comentario.denunciado = true;
//        return buscarPorID(idPerfil, comentario.id);
//    }
//}

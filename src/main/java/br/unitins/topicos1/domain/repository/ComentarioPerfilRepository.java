//package br.unitins.topicos1.domain.repository;
//
//import br.unitins.topicos1.domain.model.ComentarioPerfil;
//import br.unitins.topicos1.domain.model.Perfil;
//import br.unitins.topicos1.api.dto.response.ComentarioPerfilResponseDto;
//import io.quarkus.hibernate.orm.panache.PanacheQuery;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import io.quarkus.panache.common.Parameters;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.persistence.NoResultException;
//
//import java.util.List;
//
//@ApplicationScoped
//public class ComentarioPerfilRepository implements PanacheRepository<ComentarioPerfil> {
//    public ComentarioPerfil buscarEntidadePorId(Long idComentario, Long perfilId) {
//        return find("id = :idComentario and (remetente.id = :perfilId or destinatario.id = :perfilId)",
//                Parameters.with("idComentario", idComentario).and("perfilId", perfilId))
//                .singleResult();
//    }
//
//
//
//
//    public List<ComentarioPerfilResponseDto> buscarTodos() {
//        PanacheQuery<ComentarioPerfilResponseDto> query = ComentarioPerfil.find("SELECT cp.id, d.login.nome, r.login.nome, cp.comentario, cp.dataComentario, cp.denunciado " +
//                        "FROM ComentarioPerfil cp " +
//                        "JOIN cp.destinatario d " +
//                        "JOIN cp.remetente r")
//                .project(ComentarioPerfilResponseDto.class);
//        return query.list();
//    }
//
//    public ComentarioPerfilResponseDto buscarPorId(Long idComentario, Long perfilId) {
//        PanacheQuery<ComentarioPerfilResponseDto> query = ComentarioPerfil.find("SELECT cp.id, dest.login.nome, rem.login.nome, cp.comentario, cp.dataComentario, cp.denunciado " +
//                        "FROM ComentarioPerfil cp " +
//                        "JOIN cp.destinatario dest " +
//                        "JOIN cp.remetente rem " +
//                        "WHERE cp.id = ?1 AND (dest.id = ?2 OR rem.id = ?2) " +
//                        "ORDER BY cp.dataComentario ASC", idComentario, perfilId)
//                .project(ComentarioPerfilResponseDto.class);
//        return query.firstResultOptional().orElseThrow(NoResultException::new);
//    }
//
//
//
//    public List<ComentarioPerfilResponseDto> buscarComentariosPorPerfil(Perfil perfil) {
//        PanacheQuery<ComentarioPerfilResponseDto> query = ComentarioPerfil.find("SELECT cp.id, d.login.nome, r.login.nome, cp.comentario, cp.dataComentario, cp.denunciado " +
//                        "FROM ComentarioPerfil cp " +
//                        "JOIN cp.destinatario d " +
//                        "JOIN cp.remetente r " +
//                        "WHERE d = ?1 OR d = ?1 " +
//                        "ORDER BY cp.dataComentario ASC", perfil)
//                .project(ComentarioPerfilResponseDto.class);
//        return query.list();
//    }
//
//    public boolean existeComentario(Perfil remetente, Perfil destinatario) {
//        long count = ComentarioPerfil.count("destinatario = ?1 and remetente = ?2", destinatario, remetente);
//        return count > 0;
//    }
//
//}

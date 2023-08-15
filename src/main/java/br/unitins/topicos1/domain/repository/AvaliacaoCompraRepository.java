//package br.unitins.topicos1.domain.repository;
//
//import br.unitins.topicos1.domain.model.Anuncio;
//import br.unitins.topicos1.domain.model.AvaliacaoCompra;
//import br.unitins.topicos1.domain.model.PreCompra;
//import br.unitins.topicos1.api.dto.response.AvaliacaoCompraReponseDto;
//import br.unitins.topicos1.domain.model.Perfil;
//import io.quarkus.hibernate.orm.panache.PanacheQuery;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.NoResultException;
//import jakarta.persistence.criteria.*;
//
//import java.util.List;
//
//@ApplicationScoped
//public class AvaliacaoCompraRepository implements PanacheRepository<AvaliacaoCompra> {
//    @Inject
//    EntityManager em;
//
//    public AvaliacaoCompra buscarEntidadePorId(Long id) {
//        return find("id", id).firstResultOptional()
//                .orElseThrow(NoResultException::new);
//    }
//
//    public AvaliacaoCompraReponseDto buscarPorId(Long id) {
//        PanacheQuery<AvaliacaoCompraReponseDto> query = AvaliacaoCompra.find("SELECT ac.id, c.id AS compraId, uComprador.nome AS compradorNome, uVendedor.nome AS vendedorNome, ac.vendedor, ac.recomenda, ac.comentario, a.titulo, ac.dataAvaliacao, ac.denunciado " +
//                "FROM AvaliacaoCompra ac " +
//                "JOIN ac.preCompra c " +
//                "JOIN c.perfil pComprador " +
//                "JOIN pComprador.login uComprador " +
//                "JOIN c.anuncio a " +
//                "JOIN a.perfil pVendedor " +
//                "JOIN pVendedor.login uVendedor " +
//                "WHERE ac.id = ?1", id).project(AvaliacaoCompraReponseDto.class);
//        return query.firstResultOptional().orElseThrow(NoResultException::new);
//    }
//
//    public List<AvaliacaoCompraReponseDto> buscarTodos(Long compraId) {
//        PanacheQuery<AvaliacaoCompraReponseDto> query = AvaliacaoCompra.find("SELECT ac.id, c.id AS compraId, uComprador.nome AS compradorNome, uVendedor.nome AS vendedorNome, ac.vendedor, ac.recomenda, ac.comentario, a.titulo, ac.dataAvaliacao, ac.denunciado " +
//                "FROM AvaliacaoCompra ac " +
//                "JOIN ac.preCompra c " +
//                "JOIN c.perfil pComprador " +
//                "JOIN pComprador.login uComprador " +
//                "JOIN c.anuncio a " +
//                "JOIN a.perfil pVendedor " +
//                "JOIN pVendedor.login uVendedor " +
//                "WHERE c.id = ?1 " +
//                "ORDER BY ac.dataAvaliacao ASC", compraId).project(AvaliacaoCompraReponseDto.class);
//        return query.list();
//    }
//
//
//    public boolean avaliacaoCompraExiste(Long idCompra, boolean vendedor) {
//        long count = AvaliacaoCompra.count("preCompra.id = ?1 AND vendedor = ?2", idCompra, vendedor);
//        return count > 0;
//    }
//
//    public AvaliacaoCompra buscarAvaliacaoPorCompra(Long compraId, boolean vendedor) {
//        return find("preCompra.id = ?1 and vendedor = ?2", compraId, vendedor).firstResultOptional().orElseThrow(NoResultException::new);
//    }
//
//    public Long buscarReputacao(Perfil perfil, boolean vendedor) {
//        var cb = em.getCriteriaBuilder();
//        var cq = cb.createQuery(Long.class);
//        var avaliacaoCompra = cq.from(AvaliacaoCompra.class);
//
//        Join<AvaliacaoCompra, PreCompra> compra = avaliacaoCompra.join("preCompra");
//        Join<PreCompra, Anuncio> paginaProduto = compra.join("anuncio");
//
//        Expression<Long> caseRecomenda = cb.selectCase()
//                .when(cb.isTrue(avaliacaoCompra.get("recomenda")), cb.literal(1L))
//                .otherwise(cb.literal(-1L))
//                .as(Long.class);
//
//        cq.select(cb.sum(caseRecomenda));
//
//        if (!vendedor) {
//            cq.where(
//                    cb.equal(compra.get("perfil"), perfil),
//                    cb.equal(avaliacaoCompra.get("vendedor"), vendedor)
//            );
//        } else {
//            cq.where(
//                    cb.equal(paginaProduto.get("perfil"), perfil),
//                    cb.equal(avaliacaoCompra.get("vendedor"), vendedor)
//            );
//        }
//        Long reputacao = em.createQuery(cq).getSingleResult();
//        return reputacao != null ? reputacao : 0;
//    }
//
//}
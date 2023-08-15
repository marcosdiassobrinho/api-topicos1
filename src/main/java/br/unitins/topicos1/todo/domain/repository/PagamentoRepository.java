//package br.unitins.topicos1.todo.domain.repository;
//
//import br.unitins.topicos1.domain.enums.StatusPagamento;
//import br.unitins.topicos1.domain.model.PreCompra;
//import br.unitins.topicos1.domain.model.Pagamento;
//import br.unitins.topicos1.domain.model.Perfil;
//import io.quarkus.hibernate.orm.panache.PanacheRepository;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.criteria.*;
//import jakarta.ws.rs.NotFoundException;
//
//@ApplicationScoped
//public class PagamentoRepository implements PanacheRepository<Pagamento> {
//    @Inject
//    EntityManager em;
//
//    public Pagamento buscarPagamentoPorId(Long id) {
//        return find("id", id).firstResultOptional().orElseThrow(NotFoundException::new);
//    }
//
//    public boolean pagamentoEmAtraso(Perfil perfil) {
//        var cb = em.getCriteriaBuilder();
//        var cq = cb.createQuery(Long.class);
//        var compra = cq.from(PreCompra.class);
//
//        Join<PreCompra, Pagamento> pagamento = compra.join("pagamento");
//        Join<PreCompra, Perfil> perfilJoin = compra.join("perfil");
//
//        cq.where(cb.equal(perfilJoin, perfil),
//                cb.equal(pagamento.get("statusPagamento"), StatusPagamento.ATRASO)
//        );
//
//        cq.select(cb.count(pagamento));
//        return em.createQuery(cq).getSingleResult() > 0;
//
//    }
//}

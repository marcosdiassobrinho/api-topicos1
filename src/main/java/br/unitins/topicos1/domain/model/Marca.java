package br.unitins.topicos1.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@NamedQueries({
        @NamedQuery(name = "Marca.contarProdutos", query = "select count(p) from Produto p where p.marca.id = :id")
})
public class Marca extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    public String nome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca marca = (Marca) o;
        return Objects.equals(id, marca.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean produtoEstaEmUso() {
        return count("#Marca.contarProdutos",
                Parameters.with("id", this.id).map()) > 0;
    }


    public static boolean existeMarcaPorNome(String nome) {
        return count("nome", nome) > 0;
    }

    public static Optional<Marca> buscarPorId(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }

    public static List<Marca> buscarMarcas() {
        return findAll()
                .list();
    }
}
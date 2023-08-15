package br.unitins.topicos1.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@RequiredArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Produto.contarAnuncios", query = "select count(a) from Anuncio a where a.produto.id = :id")
})
public class Produto extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    public String nome;

    @ManyToOne
    public Marca marca;

    public Boolean denunciado = false;

    @ManyToOne
    @JoinColumn(name = "registrado_por")
    public Usuario resgitradoPor;

    @ManyToOne
    @JoinColumn(name = "alterado_por")
    public Usuario alteradoPor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Optional<Produto> buscarProdutoExistente(Produto produto) {
        return find("marca = :marca and nome like :nome",
                Parameters.with("marca", produto.marca.nome)
                        .and("nome", produto.nome))
                .firstResultOptional();
    }

    public static Optional<Produto> buscarProduto(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }

    public static List<Produto> buscarProdutos() {
        return findAll()
                .list();
    }

    public static List<Produto> buscarProdutosDenunciados() {
        return find("denunciado", true)
                .list();
    }

    public static long contarAnuncios(Long id) {
        return count("#Produto.contarAnuncios", Parameters.with("id", id).map());
    }

    public boolean produtoEstaEmUso() {
        return contarAnuncios(this.id) > 0;
    }

    public void denunciar() {
        this.denunciado = true;
    }

}

package br.unitins.topicos1.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "variacao")
@ToString
@RequiredArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Anuncio.buscarVariacoes", query = "SELECT v FROM Anuncio a JOIN a.variacoes v WHERE a.id = :id "),
})
public class Variacao extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    public String descricao;

    @Column(name = "nome_imagem")
    public String nomeImagem;

    public Double peso;

    public boolean usado;

    @Column(name = "valor_bruto")
    public Double valorBruto;

    @Column(name = "valor_liquido")
    public Double valorLiquido;

    @Column(name = "quantidade_estoque")
    public Integer quantidadeEstoque;

    public String cor;

    public Variacao(Variacao variacao) {
        this.descricao = variacao.descricao;
        this.peso = variacao.peso;
        this.usado = variacao.usado;
        this.valorBruto = variacao.valorBruto;
        this.cor = variacao.cor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variacao variacao = (Variacao) o;
        return Objects.equals(id, variacao.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Optional<Variacao> buscarPorId(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }

    public static List<Variacao> buscarTodos(Long anuncioId) {
        return findAll()
                .list();
    }

    public static List<Variacao> buscarVariacoes(Long anuncioId) {
        return find("#Anuncio.buscarVariacoes", Parameters.with("id", anuncioId).map())
                .list();
    }
}

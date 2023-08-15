package br.unitins.topicos1.domain.model;

import br.unitins.topicos1.domain.enums.StatusCompra;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "compra")
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Compra.porComprador", query = "SELECT pc FROM Compra pc JOIN pc.comprador c WHERE c = :comprador and pc.statusCompra = :status "),
})
public class Compra extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;
    @ManyToOne
    @JoinColumn(name = "comprador_id")
    public Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    public Anuncio anuncio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_compra")
    public StatusCompra statusCompra = StatusCompra.PENDENTE;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "compra_variacao",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "variacao_id"))
    @ToString.Exclude
    public List<Variacao> variacoes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pagamento_id")
    public Pagamento pagamento;

    @Column(name = "quantidade")
    public Integer quantidade;

    @Column(name = "valor")
    public Double valor;

    @ManyToOne
    public Endereco endereco;

    public Compra(Compra compra, List<Variacao> variacoes, double valor) {
        this.comprador = compra.comprador;
        this.anuncio = compra.anuncio;
        this.statusCompra = StatusCompra.ANALISE;
        this.variacoes = variacoes;
        this.quantidade = compra.quantidade;
        this.endereco = compra.endereco;
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return Objects.equals(id, compra.id);
    }

    public void finalizar() {
        this.statusCompra = StatusCompra.ANALISE;
    }

    public void reduzirValor(double valor) {
        this.valor = this.valor - valor;
    }

    public void reduzirQuantidade(Integer quantidade) {
        this.quantidade = this.quantidade - quantidade;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static Optional<Compra> buscarPorId(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }

    public static Optional<Compra> buscarCompraPorAnuncio(Anuncio anuncio, Usuario comprador) {
        return find("anuncio = ?1 and comprador = ?2 and statusCompra = PENDENTE", anuncio, comprador).
                firstResultOptional();
    }


    public static List<Compra> buscarPreCompraPessoais(Usuario usuario) {
        return find("comprador = ?1 and statusCompra = PENDENTE ", usuario)
                .list();
    }

    public static List<Compra> buscarComprasPessoais(Usuario usuario) {
        return find("comprador = ?1 and statusCompra != PENDENTE ", usuario)
                .list();
    }

    public static List<Compra> buscarVendasPessoais(Usuario usuario) {
        return find("SELECT c FROM Compra c WHERE c.anuncio.usuario = ?1", usuario)
                .list();
    }


    public boolean permissaoCompra(Usuario usuario) {
        return (!usuario.equals(this.comprador));
    }

    public void atualizarCompra(List<Variacao> variacoes, double valor, Endereco endereco) {
        this.variacoes = variacoes;
        this.valor = valor;
        this.statusCompra = StatusCompra.ANALISE;
        this.quantidade = variacoes.size();
        this.endereco = endereco;
    }
}

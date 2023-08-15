package br.unitins.topicos1.domain.model;

import br.unitins.topicos1.domain.enums.StatusAnuncio;
import br.unitins.topicos1.domain.exception.AnuncioConflitoException;
import br.unitins.topicos1.domain.model.enums.Role;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@ToString
@Table(name = "anuncio")
@RequiredArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Anuncio.porVariacao", query = "SELECT a FROM Anuncio a JOIN a.variacoes v WHERE v.id IN (:variacoes)"),
})
public class Anuncio extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String titulo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    public Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_anuncio")
    public StatusAnuncio statusAnuncio = StatusAnuncio.CRIADO;

    @Transient
    public Integer quantidadeVendida;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "anuncio_variacao",
            joinColumns = @JoinColumn(name = "anuncio_id"),
            inverseJoinColumns = @JoinColumn(name = "variacao_id"))
    @ToString.Exclude
    public List<Variacao> variacoes;

    @Column(name = "descricao_base", length = 1000)
    public String descricaoBase;

    @Column(name = "data_criacao")
    @CreationTimestamp
    public LocalDateTime dataCriacao;

    public Boolean denunciado;

    @ManyToOne
    public Endereco endereco;

    public Anuncio(Usuario usuario) {
        this.usuario = usuario;
        this.endereco = usuario.enderecos.stream()
                .filter(e -> e.principal)
                .findFirst().orElseThrow(() -> new AnuncioConflitoException("O usuário não possui nenhum endereco cadastrado."));
    }

    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDateTime.now();
    }

    public void desativar() {
        this.statusAnuncio = StatusAnuncio.DESATIVADO;
    }

    public void ativar() {
        this.statusAnuncio = StatusAnuncio.ATIVO;
    }

    public static Optional<Anuncio> buscarPorId(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }

    public boolean permissaoAnuncio(Usuario usuario) {
        return (!usuario.equals(this.usuario) && !usuario.roles.contains(Role.ADMIN));
    }

    public static List<Anuncio> buscarAnunciosPessoais(Usuario usuario) {
        return list("usuario = ?1", usuario);
    }

    public static List<Anuncio> buscarAnunciosAtivos() {
        return list("statusAnuncio = ?1", StatusAnuncio.ATIVO);
    }

    public static List<Anuncio> buscarAnunciosAnalises() {
        return list("statusAnuncio = ?1", StatusAnuncio.ANALISE);
    }


    public static Optional<Anuncio> buscarAnuncioExistente(Usuario usuario) {
        return find("usuario = ?1 and statusAnuncio != ANALISE and statusAnuncio != ATIVO and statusAnuncio != DESATIVADO", usuario)
                .firstResultOptional();
    }

    public static Optional<Anuncio> buscarAnuncioCriadoOuComplementar(Usuario usuario) {
        return find("usuario = :usuario and (statusAnuncio = :criado or statusAnuncio = :complementar)",
                Parameters.with("usuario", usuario)
                        .and("criado", StatusAnuncio.CRIADO)
                        .and("complementar", StatusAnuncio.COMPLEMENTAR))
                .firstResultOptional();
    }

    public static Optional<Anuncio> buscarAnuncioIncompleto(Usuario usuario) {
        return find("usuario = :usuario and statusAnuncio = :status",
                Parameters.with("usuario", usuario)
                        .and("status", StatusAnuncio.INCOMPLETO))
                .firstResultOptional();
    }

    public static Optional<Anuncio> buscarPorVariacoes(List<Long> ids) {
        return find("#Anuncio.porVariacao", Parameters.with("variacoes", ids))
                .firstResultOptional();
    }

    public static List<Anuncio> buscarListaPorVariacoes(List<Long> ids) {
        return find("#Anuncio.porVariacao", Parameters.with("variacoes", ids))
                .stream()
                .map(entity -> (Anuncio) entity)
                .collect(Collectors.toList());
    }


    public static Optional<Anuncio> buscarAnuncioComplementar(Usuario usuario) {
        return find("usuario = ?1 and statusAnuncio = ?2", usuario, StatusAnuncio.COMPLEMENTAR)
                .firstResultOptional();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anuncio anuncio = (Anuncio) o;
        return Objects.equals(id, anuncio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package br.unitins.topicos1.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Optional;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Endereco extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    public boolean principal;

    public String rua;

    public String bairro;

    public String estado;

    public String cidade;

    public String complemento;

    public String numero;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Endereco endereco = (Endereco) o;
        return getId() != null && Objects.equals(getId(), endereco.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Optional<Endereco> buscarPorId(Long id) {
        return find("id = ?1", id)
                .firstResultOptional();
    }
}

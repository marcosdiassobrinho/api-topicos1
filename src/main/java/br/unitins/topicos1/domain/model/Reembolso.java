package br.unitins.topicos1.domain.model;


import br.unitins.topicos1.domain.enums.StatusReembolso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Reembolso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusReembolso statusReembolso;
    @OneToOne
    Compra compra;
    private Double valor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reembolso reembolso = (Reembolso) o;
        return getId() != null && Objects.equals(getId(), reembolso.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

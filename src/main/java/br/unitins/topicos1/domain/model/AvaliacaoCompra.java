package br.unitins.topicos1.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@ToString
@Table(name = "avaliacao_compra")
public class AvaliacaoCompra extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    public boolean recomenda;

    public LocalDateTime dataAvaliacao;

    public boolean vendedor;

    public boolean denunciado;

    public String comentario;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "compra_id")
    public Compra compra;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvaliacaoCompra that = (AvaliacaoCompra) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

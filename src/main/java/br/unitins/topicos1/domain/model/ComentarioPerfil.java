package br.unitins.topicos1.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@ToString
@Table(name = "comentario_perfil")
public class ComentarioPerfil extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    public Usuario remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    public Usuario destinatario;

    public String comentario;
    @Column(name = "data_comentario")
    public LocalDateTime dataComentario;

    public boolean denunciado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComentarioPerfil that = (ComentarioPerfil) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

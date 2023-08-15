package br.unitins.topicos1.domain.model;

import br.unitins.topicos1.domain.enums.FormaPagamento;
import br.unitins.topicos1.domain.enums.StatusPagamento;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@ToString
@RequiredArgsConstructor
public class Pagamento extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    public StatusPagamento statusPagamento;

    @OneToMany(mappedBy = "pagamento")
    public List<Compra> compra;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    public FormaPagamento formaPagamento;

    @Column(name = "data_emissao")
    public LocalDateTime dataEmissao;

    public Double valorTotal;

    @Transient
    public int quantParcelas;

    @OneToMany(mappedBy = "pagamento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Parcela> parcelas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pagamento pagamento = (Pagamento) o;
        return this.id != null && Objects.equals(this.id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
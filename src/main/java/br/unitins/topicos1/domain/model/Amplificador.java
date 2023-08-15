package br.unitins.topicos1.domain.model;

import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@ToString
@RequiredArgsConstructor
@Entity
public class Amplificador extends Produto {
    public Double impendancia;
    public String entrada;
    public String saida;
}

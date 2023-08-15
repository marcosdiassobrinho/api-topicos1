package br.unitins.topicos1.domain.model;

import jakarta.persistence.Entity;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@ToString
@RequiredArgsConstructor
@Entity
public class Inear extends Produto {
    public String driver;
    public Double impendancia;
    public String pino;
    public String descricaoBase;

}

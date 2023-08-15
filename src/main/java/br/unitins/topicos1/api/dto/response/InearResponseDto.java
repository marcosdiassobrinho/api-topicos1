package br.unitins.topicos1.api.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class InearResponseDto extends ProdutoResponse {

     String driver;

     Double impendancia;

     String pino;

     String descricaoBase;

}

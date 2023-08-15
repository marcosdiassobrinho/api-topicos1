package br.unitins.topicos1.api.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class AmplificadorResponseDto extends ProdutoResponse {
    Double impendancia;
    String entrada;
    String saida;
}

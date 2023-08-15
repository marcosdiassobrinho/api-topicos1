package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoCompraDto(@NotNull Long perfilId, @NotBlank String comentario, @NotNull Boolean recomenda) {

}

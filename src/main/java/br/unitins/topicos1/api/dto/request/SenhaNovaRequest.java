package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SenhaNovaRequest(@NotBlank String senhaVelha, @NotBlank String senhaNova) {

}

package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequest(@NotBlank String cidade, String complemento, @NotBlank String estado,
                              @NotBlank String bairro, @NotBlank String rua,
                              @NotNull Boolean principal) {
}

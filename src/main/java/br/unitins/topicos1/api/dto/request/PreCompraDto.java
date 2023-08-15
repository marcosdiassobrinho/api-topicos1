package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PreCompraDto(@NotEmpty List<Long> variacoes) {
}

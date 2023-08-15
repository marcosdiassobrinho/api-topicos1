package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record VariacaoDto(@NotBlank String descricao, @PositiveOrZero Double peso, @NotNull Boolean usado,
                          @PositiveOrZero Double valorBruto, @PositiveOrZero Integer quantidadeEstoque, String cor) {
}

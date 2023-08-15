package br.unitins.topicos1.api.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

public record VariacaoResponseDto(Long id, String descricao, Double peso, Double valorLiquido,
                                  Integer quantidadeEstoque, boolean usado, String cor) {
}

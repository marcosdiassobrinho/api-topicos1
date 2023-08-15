package br.unitins.topicos1.api.dto.request;


import jakarta.validation.constraints.NotBlank;

public record AnuncioDto(@NotBlank String titulo, String descricaoBase) {
}

package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UsuarioRequest(@NotBlank String nome, @CPF String cpf) {
}

package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record CadastroRequest(@NotBlank String login, @NotBlank String senha, @NotBlank String nome, @CPF String cpf,
                              @Email String email) {
}

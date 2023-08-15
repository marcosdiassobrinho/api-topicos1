package br.unitins.topicos1.api.dto.request;

import jakarta.validation.constraints.*;

public record LoginDto(@NotBlank @Size(min = 8) String login, @NotBlank @Size(min = 8) String senha,
                       @Email String email) {

}

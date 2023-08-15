package br.unitins.topicos1.api.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;
@RegisterForReflection
public record ComentarioPerfilResponseDto(Long id, String destinatario, String remetente, String comentario,
                                          LocalDateTime data, boolean denunciado) {
}

package br.unitins.topicos1.api.dto.response;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;
@RegisterForReflection
public record AvaliacaoCompraReponseDto(Long idAvaliacao, Long idCompra, String usuarioComprador,
                                        String usuarioVendedor,
                                        boolean vendedor, boolean recomenda, String comentario, String tituloAnuncio,
                                        LocalDateTime dataAvalicaco, boolean denunciado) {


}

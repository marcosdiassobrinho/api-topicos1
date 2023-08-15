package br.unitins.topicos1.api.exception;
import lombok.Value;

import jakarta.json.bind.annotation.JsonbProperty;
@Value
public class ApiError {
    @JsonbProperty("type")
    String type;

    @JsonbProperty("title")
    String title;

    @JsonbProperty("status")
    int status;

    @JsonbProperty("detail")
    String detail;
}

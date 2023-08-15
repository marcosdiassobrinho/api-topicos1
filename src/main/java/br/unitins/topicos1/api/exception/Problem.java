package br.unitins.topicos1.api.exception;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {
    private OffsetDateTime timestamp;
    private int status;
    private String title;
    private String detail;
    private String userMessage;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {
        private String name;
        private String userMessage;
    }
}


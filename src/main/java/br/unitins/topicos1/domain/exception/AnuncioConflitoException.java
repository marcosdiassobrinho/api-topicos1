package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class AnuncioConflitoException extends ConflictException {
    public AnuncioConflitoException(String message) {
        super(message);
    }
}

package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class MarcaConflitoException extends ConflictException {
    public MarcaConflitoException(String message) {
        super(message);
    }
}

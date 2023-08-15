package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class VariacaoConflitoException extends ConflictException {
    public VariacaoConflitoException(String message) {
        super(message);
    }
}

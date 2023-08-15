package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class PerfilConflitoException extends ConflictException {
    public PerfilConflitoException(String message) {
        super(message);
    }
}

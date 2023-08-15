package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class LoginConflitoException extends ConflictException {
    public LoginConflitoException(String message) {
        super(message);
    }
}

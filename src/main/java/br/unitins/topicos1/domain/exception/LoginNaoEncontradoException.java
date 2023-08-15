package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;


public class LoginNaoEncontradoException extends NotFoundEntityException {
    public LoginNaoEncontradoException(String message) {
        super(message);
    }
}

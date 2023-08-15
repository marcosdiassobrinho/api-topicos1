package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;


public class PerfiNaoEncontradoException extends NotFoundEntityException {
    public PerfiNaoEncontradoException(String message) {
        super(message);
    }
}

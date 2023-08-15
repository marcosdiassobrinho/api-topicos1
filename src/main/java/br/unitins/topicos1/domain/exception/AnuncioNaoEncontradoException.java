package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;

public class AnuncioNaoEncontradoException extends NotFoundEntityException {
    public AnuncioNaoEncontradoException(String message) {
        super(message);
    }
}

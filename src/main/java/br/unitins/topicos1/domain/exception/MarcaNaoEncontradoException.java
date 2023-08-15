package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;

public class MarcaNaoEncontradoException extends NotFoundEntityException {
    public MarcaNaoEncontradoException(String message) {
        super(message);
    }
}

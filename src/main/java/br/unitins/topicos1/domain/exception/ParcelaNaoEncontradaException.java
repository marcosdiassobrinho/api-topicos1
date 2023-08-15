package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;

public class ParcelaNaoEncontradaException extends NotFoundEntityException {
    public ParcelaNaoEncontradaException(String message) {
        super(message);
    }
}

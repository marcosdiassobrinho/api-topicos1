package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;


public class CompraNaoEncontradoException extends NotFoundEntityException {
    public CompraNaoEncontradoException(String message) {
        super(message);
    }
}

package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;

public class AvaliacaoCompraNaoEncontradaException extends NotFoundEntityException {
    public AvaliacaoCompraNaoEncontradaException(String message) {
        super(message);
    }
}

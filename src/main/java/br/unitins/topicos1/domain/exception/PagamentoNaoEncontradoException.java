package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;

public class PagamentoNaoEncontradoException extends NotFoundEntityException {
    public PagamentoNaoEncontradoException(String message) {
        super(message);
    }
}

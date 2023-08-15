package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;


public class VariacaoNaoEncontradoException extends NotFoundEntityException {
    public VariacaoNaoEncontradoException(String message) {
        super(message);
    }
}

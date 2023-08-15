package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;


public class ProdutoNaoEncontradoException extends NotFoundEntityException {
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}

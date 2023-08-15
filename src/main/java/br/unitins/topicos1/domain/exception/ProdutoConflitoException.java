package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;

public class ProdutoConflitoException extends ConflictException {
    public ProdutoConflitoException(String message) {
        super(message);
    }
}

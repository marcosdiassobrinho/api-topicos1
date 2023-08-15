package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class PagamentoConflitoException extends ConflictException {
    public PagamentoConflitoException(String message) {
        super(message);
    }
}

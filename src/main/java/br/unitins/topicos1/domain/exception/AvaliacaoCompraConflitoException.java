package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.ConflictException;


public class AvaliacaoCompraConflitoException extends ConflictException {
    public AvaliacaoCompraConflitoException(String message) {
        super(message);
    }
}

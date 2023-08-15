package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;


public class ComentarioPerfilNaoEncontradoException extends NotFoundEntityException {
    public ComentarioPerfilNaoEncontradoException(String message) {
        super(message);
    }
}

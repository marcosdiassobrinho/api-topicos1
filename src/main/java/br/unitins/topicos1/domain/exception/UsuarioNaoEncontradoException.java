package br.unitins.topicos1.domain.exception;

import br.unitins.topicos1.api.exception.NotFoundEntityException;

public class UsuarioNaoEncontradoException extends NotFoundEntityException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
}

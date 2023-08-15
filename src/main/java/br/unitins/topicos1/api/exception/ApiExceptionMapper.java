package br.unitins.topicos1.api.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

import org.jboss.logging.Logger;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOG = Logger.getLogger(ApiExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof NotFoundEntityException) {
            return handleNotFoundException((NotFoundEntityException) exception);
        } else if (exception instanceof ConflictException) {
            return handleConflictException((ConflictException) exception);
        } else if (exception instanceof BadRequestException) {
            return handleBadRequestException((BadRequestException) exception);
        } else if (exception instanceof JsonProcessingException) {
            return handleJsonProcessingException((JsonProcessingException) exception);
        } else if (exception instanceof ForbiddenException) {
            return handleForbiddenException((ForbiddenException) exception);
        }
        return defaultErrorResponse(exception);
    }

    private Response handleNotFoundException(NotFoundEntityException exception) {
        LOG.fatal("Recurso não encontrado: " + exception.getMessage());
        ApiError apiError = new ApiError(
                "https://localhost:8080/not-found",
                "Recurso não encontrado.",
                Response.Status.NOT_FOUND.getStatusCode(),
                exception.getMessage()
        );
        return Response.status(Response.Status.NOT_FOUND)
                .entity(apiError)
                .build();
    }

    private Response handleInvalidFormatException(InvalidFormatException exception) {
        LOG.fatal("Mensagem incompreensível: " + exception.getMessage());
        String path = exception.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        String detail = String.format("A campo '%s' recebeu um formato inválido: '%s'. Corrija-o informando um valor compatível com o formato %s.",
                path, exception.getValue(), exception.getTargetType().getSimpleName());

        ApiError apiError = new ApiError(
                "https://localhost:8080/invalid-format",
                "Mensagem incompreensível",
                Response.Status.BAD_REQUEST.getStatusCode(),
                detail
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(apiError)
                .build();
    }

    private Response handleJsonProcessingException(JsonProcessingException exception) {
        LOG.fatal("Mensagem incompreensível: " + exception.getMessage());
        if (exception instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) exception);
        }

        ApiError apiError = new ApiError(
                "https://localhost:8080/message-not-readable",
                "Mensagem incompreensível",
                Response.Status.BAD_REQUEST.getStatusCode(),
                "O corpo da requisição está inválido. Verifique o erro de sintaxe."
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(apiError)
                .build();
    }

    private Response handleConflictException(ConflictException exception) {
        LOG.fatal("Conflito de recursos: " + exception.getMessage());
        ApiError apiError = new ApiError(
                "https://localhost:8080/conflict",
                "Conflito de recursos.",
                Response.Status.CONFLICT.getStatusCode(),
                exception.getMessage()
        );

        return Response.status(Response.Status.CONFLICT)
                .entity(apiError)
                .build();
    }

    private Response handleBadRequestException(BadRequestException exception) {
        LOG.fatal("Requisição inválida: " + exception.getMessage());
        ApiError apiError = new ApiError(
                "https://localhost:8080/bad-request",
                "Requisição inválida.",
                Response.Status.BAD_REQUEST.getStatusCode(),
                exception.getMessage()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(apiError)
                .build();
    }

    private Response handleForbiddenException(ForbiddenException exception) {
        LOG.fatal("Erro sem permissao: " + exception.getMessage());
        ApiError apiError = new ApiError(
                "https://localhost:8080/forbidden",
                "Acesso negado.",
                Response.Status.FORBIDDEN.getStatusCode(),
                exception.getMessage()
        );

        return Response.status(Response.Status.FORBIDDEN)
                .entity(apiError)
                .build();
    }

    private Response defaultErrorResponse(Exception exception) {
        LOG.fatal("Erro Fatal: " + exception.getMessage());
        ApiError apiError = new ApiError(
                "https://localhost:8080/internal-error",
                "Erro Fatal",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                exception.getMessage()
        );


        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(apiError)
                .build();
    }


}

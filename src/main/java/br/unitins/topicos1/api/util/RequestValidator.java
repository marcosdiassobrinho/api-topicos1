package br.unitins.topicos1.api.util;

import br.unitins.topicos1.api.exception.BadRequestException;
import br.unitins.topicos1.api.exception.NegocioException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.StringJoiner;

@ApplicationScoped
public class RequestValidator {
    @Inject
    Validator validator;

    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ", "Erro de validação: ", ".");
            for (ConstraintViolation<T> violation : violations) {
                joiner.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new NegocioException(joiner.toString());
        }
    }

    public <T> void validateProperty(T dto, String propertyName) {
        Set<ConstraintViolation<T>> violations = validator.validateProperty(dto, propertyName);

        if (!violations.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ", "Erro de validação: ", ".");
            for (ConstraintViolation<T> violation : violations) {
                joiner.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new BadRequestException(joiner.toString());
        }
    }

    public <T> void validateNonNullProperties(T dto) {
        Field[] fields = dto.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(dto);

                if (value != null) {
                    validateProperty(dto, field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new BadRequestException("Erro ao acessar a propriedade: " + field.getName());
            }
        }
    }


}

package org.example.buprototype.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {

    public ValidationException(Set<ConstraintViolation<Object>> violations) {
        super(violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","))
        );
    }

    public ValidationException(String message) {
        super(message);
    }
}

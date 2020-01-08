package org.example.buprototype.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Component
public class BeanValidator {

    private Validator validator;

    public BeanValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public void validateBean(Object bean) {
        requireNonNull(bean, "Bean may not be null");

        Set<ConstraintViolation<Object>> violations = validator.validate(bean);

        if(!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }
}

package com.ecommerce.product_service.validation.validator;

import com.ecommerce.product_service.validation.NotEmptyObject;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class NotEmptyObjectValidator implements ConstraintValidator<NotEmptyObject, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
       return Objects.nonNull(o);
    }
}

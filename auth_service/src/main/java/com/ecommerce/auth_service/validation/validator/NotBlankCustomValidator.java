package com.ecommerce.auth_service.validation.validator;

import com.ecommerce.auth_service.validation.NotBlankCustom;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankCustomValidator implements ConstraintValidator<NotBlankCustom,String> {
    @Override
    public void initialize(NotBlankCustom constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.contains(" ");
    }
}

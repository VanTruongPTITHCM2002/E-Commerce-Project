package com.ecommerce.product_service.validation;

import com.ecommerce.product_service.validation.validator.NotEmptyObjectValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyObjectValidator.class)
public @interface NotEmptyObject {
    String message() default "Request Body must not be empty";
    Class<?>[] groups() default  {};
    Class<? extends Payload>[] payload() default {};
}

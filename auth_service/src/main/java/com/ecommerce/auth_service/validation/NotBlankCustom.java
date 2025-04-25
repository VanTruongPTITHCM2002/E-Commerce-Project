package com.ecommerce.auth_service.validation;

import com.ecommerce.auth_service.validation.validator.NotBlankCustomValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankCustomValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankCustom {
    String message() default "Don't have blank";

    Class<?>[] groups() default {};
    Class<? extends  Payload>[] payload() default {};
}

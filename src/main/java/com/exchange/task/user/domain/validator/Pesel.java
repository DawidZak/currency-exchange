package com.exchange.task.user.domain.validator;


import jakarta.validation.Payload;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PeselValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pesel {
    String message() default "Invalid PESEL number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
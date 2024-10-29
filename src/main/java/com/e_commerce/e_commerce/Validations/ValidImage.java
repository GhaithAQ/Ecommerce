package com.e_commerce.e_commerce.Validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {

    public String value() default "Ghaith1";

    String message() default "The image is required and must be a valid file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.e_commerce.e_commerce.Validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@jakarta.validation.Constraint(validatedBy = ImageSizeValidator.class)
public @interface ValidImageSize {

    public String value() default "Ghaith";

    String message() default "Invalid file size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

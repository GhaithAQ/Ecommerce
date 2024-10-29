package com.e_commerce.e_commerce.Validations;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolationException;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public void initialize(ValidImage constraintAnnotation) throws ConstraintViolationException {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) throws ConstraintViolationException {
        // Invalid if the file is null or empty
        return !(file == null || file.isEmpty());
    }
}

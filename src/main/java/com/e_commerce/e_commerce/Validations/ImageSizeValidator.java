package com.e_commerce.e_commerce.Validations;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageSizeValidator implements ConstraintValidator<ValidImageSize, MultipartFile> {

    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5MB

    @Override
    public void initialize(ValidImageSize constraintAnnotation) {
        // You can initialize any parameters from the annotation if needed
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Empty files are considered valid; handle as per your requirement
        }

        if (file.getSize() > MAX_SIZE) {
            // Set the error message and mark the validation as failed
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size exceeds the maximum limit of 5MB").addConstraintViolation();
            return false;
        }

        return true; // File size is within the allowed limit
    }
}

package com.accenture.configuration.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinPagesValidator implements ConstraintValidator<MinPages, Integer> {

    @Override
    public void initialize(MinPages constraintAnnotation) {
        // Puedes inicializar valores si es necesario
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // Si el valor es null, la validación fallará, ya que el campo no es obligatorio
        if (value == null) {
            return false;
        }
        // Validar si el valor es al menos 100
        return value >= 100;
    }
}
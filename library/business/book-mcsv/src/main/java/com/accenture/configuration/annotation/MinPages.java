package com.accenture.configuration.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinPagesValidator.class)  // Vincula esta anotación al validador
public @interface MinPages {

    // Mensaje de error si no pasa la validación
    String message() default "El número de páginas debe ser al menos 100";

    // Grupo de validación (se puede usar para agrupar validaciones)
    Class<?>[] groups() default {};

    // Carga útil adicional para la validación
    Class<? extends Payload>[] payload() default {};

}
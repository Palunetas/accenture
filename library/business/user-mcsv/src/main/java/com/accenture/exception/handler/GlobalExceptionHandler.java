package com.accenture.exception.handler;

import com.accenture.exception.BadRequestException;
import com.accenture.exception.NotFoundException;
import com.accenture.exception.model.ErrorConstants;
import com.accenture.exception.model.ErrorResponse;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        // El código de error se obtiene de las constantes
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorConstants.BAD_REQUEST_CODE,  // Código de error
                ex.getErrorMessages()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(NotFoundException ex) {
        // El código de error se obtiene de las constantes
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorConstants.NOT_FOUND,  // Código de error
                Collections.singletonList(ex.getMessage())  // Mensaje específico de la excepción
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}

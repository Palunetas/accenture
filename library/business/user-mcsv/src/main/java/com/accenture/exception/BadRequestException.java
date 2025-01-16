package com.accenture.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
    private List<String> errorMessages;

    public BadRequestException(List<String> errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}

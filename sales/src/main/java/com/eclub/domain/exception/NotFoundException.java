package com.eclub.domain.exception;

/**
 * Exception that is thrown when domain object is not found
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

package com.assessment.exception;

/**
 * The type User already exists exception.
 */
public class UserValidationException extends RuntimeException {
    /**
     * Instantiates a new User already exists exception.
     *
     * @param message the message
     */
    public UserValidationException(String message) {
        super(message);
    }
}

package com.assessment.exception;

/**
 * Exception thrown when a requested resource is not found in the system.
 * This exception is typically thrown when attempting to retrieve, update, or delete
 * an entity that does not exist in the database.
 *
 * @author Assessment Team
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining what resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
} 
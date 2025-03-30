package com.assessment.exception;

import java.time.LocalDateTime;

/**
 * A class representing a standardized error response structure.
 * This class is used to provide consistent and structured error information to API clients.
 * It encapsulates HTTP status codes, error messages, and timestamps for error tracking.
 *
 * @author Assessment Team
 * @version 1.0
 */
public class ErrorResponse {
    /**
     * The HTTP status code associated with the error.
     * This typically corresponds to standard HTTP status codes (e.g., 400, 404, 500).
     */
    private int status;

    /**
     * A detailed description of the error that occurred.
     * This message should provide clear information about what went wrong.
     */
    private String message;

    /**
     * The exact date and time when the error occurred.
     * This helps in tracking and debugging issues.
     */
    private LocalDateTime timestamp;

    /**
     * Constructs a new ErrorResponse with the specified details.
     * This constructor initializes all fields including the timestamp.
     *
     * @param status    The HTTP status code of the error
     * @param message   A detailed description of the error
     * @param timestamp The exact time when the error occurred
     */
    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Constructs a new ErrorResponse with status and message only.
     * This constructor is used when timestamp is not required or will be set later.
     *
     * @param status  The HTTP status code of the error
     * @param message A detailed description of the error
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Gets the HTTP status code of the error.
     *
     * @return The HTTP status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code of the error.
     *
     * @param status The HTTP status code to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the detailed error message.
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the detailed error message.
     *
     * @param message The error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the timestamp when the error occurred.
     *
     * @return The error timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the error occurred.
     *
     * @param timestamp The timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 
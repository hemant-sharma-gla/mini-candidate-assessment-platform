package com.assessment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for user registration requests.
 * Contains all necessary fields to create a new user account.
 */
@Data
public class RegisterRequest {
    /**
     * Username for the new account
     * Must be between 3 and 50 characters
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * Email address for the new account
     * Must be in valid email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide valid email id")
    private String email;

    /**
     * Password for the new account
     * Must be at least 6 characters long
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
} 
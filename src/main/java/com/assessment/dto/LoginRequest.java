package com.assessment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user login requests.
 * Contains the necessary credentials for user authentication.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    /**
     * Username for authentication
     * Cannot be blank
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * Password for authentication
     * Cannot be blank
     */
    @NotBlank(message = "Password is required")
    private String password;
} 
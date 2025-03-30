package com.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for authentication responses.
 * Contains the JWT token and user information after successful authentication.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    /**
     * JWT token for subsequent authenticated requests
     */
    private String token;

    /**
     * Username of the authenticated user
     */
    private String username;

    /**
     * Array of roles assigned to the user
     */
    private String[] roles;
} 
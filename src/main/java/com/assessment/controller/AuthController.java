package com.assessment.controller;

import com.assessment.dto.AuthResponse;
import com.assessment.dto.LoginRequest;
import com.assessment.dto.RegisterRequest;
import com.assessment.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling authentication-related operations.
 * This controller provides endpoints for user login, registration, and logout functionality.
 * All endpoints are prefixed with "/api/auth".
 *
 * @see AuthResponse
 * @see LoginRequest
 * @see RegisterRequest
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for Authentication Management")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Service responsible for handling authentication-related operations.
     * Provides functionality for user login, registration, and logout.
     */
    private final AuthService authService;

    /**
     * Authenticates a user and returns a JWT token upon successful login.
     *
     * @param request The login credentials containing username/email and password
     * @return ResponseEntity containing the authentication response with JWT token
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are invalid
     * @throws jakarta.validation.ConstraintViolationException                     if request validation fails
     */
    @Operation(
            summary = "User Login",
            description = "Authenticates a user and returns a JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Login credentials", required = true)
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Registers a new user and returns a JWT token upon successful registration.
     *
     * @param request The registration details containing user information
     * @return ResponseEntity containing the authentication response with JWT token
     * @throws org.springframework.security.authentication.BadCredentialsException if registration fails
     * @throws jakarta.validation.ConstraintViolationException                     if request validation fails
     * @throws org.springframework.dao.DataIntegrityViolationException             if username/email already exists
     */
    @Operation(
            summary = "User Registration",
            description = "Registers a new user and returns a JWT token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Parameter(description = "Registration details", required = true)
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Logs out the current user and invalidates their session.
     *
     * @param request The HTTP request containing the user's session information
     * @return ResponseEntity with no content indicating successful logout
     * @throws org.springframework.security.core.AuthenticationException if user is not authenticated
     */
    @Operation(
            summary = "User Logout",
            description = "Logs out the current user and invalidates the session"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged out"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }
} 
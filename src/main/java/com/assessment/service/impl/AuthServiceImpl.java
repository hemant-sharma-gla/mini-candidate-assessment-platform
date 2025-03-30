package com.assessment.service.impl;

import com.assessment.constants.ApplicationConstants;
import com.assessment.dto.AuthResponse;
import com.assessment.dto.LoginRequest;
import com.assessment.dto.RegisterRequest;
import com.assessment.entity.Role;
import com.assessment.entity.User;
import com.assessment.exception.ResourceNotFoundException;
import com.assessment.exception.UserValidationException;
import com.assessment.repository.RoleRepository;
import com.assessment.repository.UserRepository;
import com.assessment.security.JwtService;
import com.assessment.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.assessment.constants.ApplicationConstants.ROLE_USER;
import static com.assessment.constants.ErrorConstants.DEFAULT_ROLE_NOT_FOUND;
import static com.assessment.constants.ErrorConstants.EMAIL_ALREADY_REGISTERED;
import static com.assessment.constants.ErrorConstants.USERNAME_ALREADY_TAKEN;
import static com.assessment.constants.ErrorConstants.USER_NOT_FOUND;

/**
 * Service class handling user authentication operations.
 * This service manages user registration, login, and JWT token generation.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    /**
     * Repository for managing User entities in the database.
     * Handles CRUD operations and custom queries for User data.
     */
    private final UserRepository userRepository;

    /**
     * Service for encoding and validating passwords.
     * Used to securely hash passwords during user registration and verify them during login.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Service for handling JWT (JSON Web Token) operations.
     * Responsible for token generation, validation, and extraction of user information.
     */
    private final JwtService jwtService;

    /**
     * Spring Security's authentication manager.
     * Handles the authentication process for user login requests.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Repository for managing Role entities in the database.
     * Handles CRUD operations and queries for user roles and permissions.
     */
    private final RoleRepository roleRepository;

    /**
     * Service for managing blacklisted JWT tokens.
     * Handles token invalidation during logout and prevents reuse of invalidated tokens.
     */
    private final TokenBlacklistServiceImpl tokenBlacklistService;

    /**
     * Registers a new user in the system.
     * This method:
     * 1. Validates the registration request
     * 2. Creates a new user with encoded password
     * 3. Generates a JWT token for the new user
     *
     * @param request the registration request containing user details
     * @return AuthResponse containing the JWT token and user information
     * @throws IllegalArgumentException if username or email is already taken
     */
    @Override
    public AuthResponse register(RegisterRequest request) {
        validateRegistration(request);
        User user = createUser(request);
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt, user.getUsername(), new String[]{ROLE_USER});
    }

    /**
     * Authenticates a user and generates a JWT token.
     * This method:
     * 1. Validates the user credentials
     * 2. Generates a new JWT token upon successful authentication
     *
     * @param request the login request containing credentials
     * @return AuthResponse containing the JWT token and user information
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt, user.getUsername(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .toArray(String[]::new));
    }

    /**
     * Logs out a user by invalidating their JWT token.
     *
     * @param request the HTTP request containing the JWT token
     */
    @Override
    public void logout(HttpServletRequest request) {
        String token = getJwtFromRequest(request);
        if (StringUtils.hasText(token)) {
            tokenBlacklistService.blacklistToken(token);
        }
        SecurityContextHolder.clearContext();
    }

    /**
     * Validates the registration request by checking for existing username and email.
     *
     * @param request the registration request to validate
     * @throws IllegalArgumentException if username or email is already taken
     */
    private void validateRegistration(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserValidationException(USERNAME_ALREADY_TAKEN);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserValidationException(EMAIL_ALREADY_REGISTERED);
        }
    }

    /**
     * Creates a new user with the provided registration details.
     *
     * @param request the registration request containing user details
     * @return the created user
     * @throws RuntimeException if the default user role is not found
     */
    private User createUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByName(ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException(DEFAULT_ROLE_NOT_FOUND));
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     *
     * @param request the HTTP request containing the Authorization header
     * @return the JWT token if present and properly formatted, null otherwise
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(ApplicationConstants.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ApplicationConstants.BEARER_SPACE)) {
            return bearerToken.substring(7);
        }
        return null;
    }
} 
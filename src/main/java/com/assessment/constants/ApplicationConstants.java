package com.assessment.constants;

/**
 * Application-wide constants used throughout the application.
 * This class contains all the constant values that are used across different components.
 * It provides a centralized location for all constant values to ensure consistency
 * and maintainability across the application.
 */
public class ApplicationConstants {

    /**
     * Secret key used for JWT token generation and validation.
     * In production, this should be replaced with a secure, environment-specific key.
     */
    public static final String JWT_SECRET_KEY = "your_jwt_secret_key_here_make_it_long_and_secure_in_production";

    /**
     * JWT token expiration time in milliseconds.
     * Set to 24 hours (86400000 milliseconds).
     */
    public static final long JWT_EXPIRATION = 86400000L; // 24 hours in milliseconds

    /**
     * Base URL pattern for authentication-related endpoints.
     * Matches all paths starting with /api/auth/
     */
    public static final String API_AUTH_BASE = "/api/auth/**";

    /**
     * Base URL pattern for admin-related endpoints.
     * Matches all paths starting with /api/admin/
     */
    public static final String API_ADMIN_BASE = "/api/admin/**";

    /**
     * Base URL pattern for candidate-related endpoints.
     * Matches all paths starting with /api/candidate/
     */
    public static final String API_CANDIDATE_BASE = "/api/candidate/**";

    /**
     * Base URL pattern for Swagger documentation endpoints.
     * Matches all paths containing /swagger/
     */
    public static final String API_SWAGGER_BASE = "**/swagger/**";

    /**
     * Role identifier for regular users of the application.
     */
    public static final String ROLE_USER = "ROLE_USER";
    /**
     * The constant USER.
     */
    public static final String USER = "USER";
    /**
     * The constant ADMIN.
     */
    public static final String ADMIN = "ADMIN";
    /**
     * The constant ADMIN_LOWERCASE.
     */
    public static final String ADMIN_LOWERCASE = "admin";

    /**
     * Role identifier for administrative users of the application.
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Default number of questions to be included in a quiz.
     */
    public static final int DEFAULT_QUIZ_QUESTIONS = 5;

    /**
     * Maximum age in seconds for CORS preflight requests.
     * Set to 1 hour (3600 seconds).
     */
    public static final long CORS_MAX_AGE = 3600L;

    /**
     * The constant BEARER_AUTH.
     */
    public static final String BEARER_AUTH = "Bearer Auth";
    /**
     * The constant BEARER.
     */
    public static final String BEARER = "Bearer";
    /**
     * The constant BEARER_SPACE.
     */
    public static final String BEARER_SPACE = "Bearer ";
    /**
     * The constant JWT.
     */
    public static final String JWT = "JWT";
    /**
     * The constant ASSIGNMENT_SERVICE.
     */
    public static final String ASSIGNMENT_SERVICE = "Assignment Service";
    /**
     * The constant ASSIGNMENT_URL.
     */
    public static final String ASSIGNMENT_URL = "https://github.com/hemant-sharma-gla/mini-candidate-assessment-platform";
    /**
     * The constant SERVICE_BASE_URL.
     */
    public static final String SERVICE_BASE_URL = "http://localhost:8080";
    /**
     * The constant SERVICE_VERSION.
     */
    public static final String SERVICE_VERSION = "1.0.0";
    /**
     * The constant ASSIGNMENT_SERVICE_DESCRIPTION.
     */
    public static final String ASSIGNMENT_SERVICE_DESCRIPTION =
            "This Swagger Interface details out the APIs for Assignment Service";
    /**
     * The constant ADMINISTRATOR_ROLE.
     */
    public static final String ADMINISTRATOR_ROLE = "Administrator role";
    /**
     * The constant USER_ROLE.
     */
    public static final String USER_ROLE = "User role";
    /**
     * The constant ADMIN_DEFAULT_MAIL.
     */
    public static final String ADMIN_DEFAULT_MAIL = "admin@example.com";
    /**
     * The constant ADMIN_DEFAULT_PASSWORD.
     */
    public static final String ADMIN_DEFAULT_PASSWORD = "admin123";
    /**
     * The constant ALLOW_ALL_ORIGIN.
     */
    public static final String ALLOW_ALL_ORIGIN = "*";
    /**
     * The constant GET.
     */
    public static final String GET = "GET";
    /**
     * The constant POST.
     */
    public static final String POST = "POST";
    /**
     * The constant PUT.
     */
    public static final String PUT = "PUT";
    /**
     * The constant DELETE.
     */
    public static final String DELETE = "DELETE";
    /**
     * The constant OPTIONS.
     */
    public static final String OPTIONS = "OPTIONS";
    /**
     * The constant AUTHORIZATION.
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * The constant CONTENT_TYPE.
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * The constant X_REQUESTED_WITH.
     */
    public static final String X_REQUESTED_WITH = "X-Requested-With";
    /**
     * The constant CORS_PATTERN.
     */
    public static final String CORS_PATTERN = "/**";

    /**
     * Private constructor to prevent instantiation of this utility class.
     * This class is meant to be used as a container for constants only.
     */
    private ApplicationConstants() {
    }
} 
package com.assessment.constants;

/**
 * The type Error constants.
 */
public class ErrorConstants {
    /**
     * The constant USER_NOT_FOUND.
     */
    public static final String USER_NOT_FOUND = "User not found";
    /**
     * The constant QUESTION_NOT_FOUND.
     */
    public static final String QUESTION_NOT_FOUND = "Question not found";
    /**
     * The constant DEFAULT_ROLE_NOT_FOUND.
     */
    public static final String DEFAULT_ROLE_NOT_FOUND = "Default role not found";
    /**
     * The constant ACCESS_DENIED.
     */
    public static final String ACCESS_DENIED = "Access denied: %s";
    /**
     * The constant ADMIN_CANNOT_SUBMIT_QUIZ.
     */
    public static final String ADMIN_CANNOT_SUBMIT_QUIZ = "Admin can not submit the quiz";
    /**
     * The constant USERNAME_ALREADY_TAKEN.
     */
    public static final String USERNAME_ALREADY_TAKEN = "User is already exists with this username";
    /**
     * The constant EMAIL_ALREADY_REGISTERED.
     */
    public static final String EMAIL_ALREADY_REGISTERED = "Email is already registered exists with this email";
    /**
     * The constant USER_ALREADY_SUBMITTED_QUIZ.
     */
    public static final String USER_ALREADY_SUBMITTED_QUIZ = "User has already submitted the quiz";
    /**
     * The constant ADMIN_ROLE_NOT_FOUND.
     */
    public static final String ADMIN_ROLE_NOT_FOUND = "Admin role not found";

    /**
     * Private constructor to prevent instantiation of this utility class.
     * This class is meant to be used as a container for constants only.
     */
    private ErrorConstants() {
    }
}

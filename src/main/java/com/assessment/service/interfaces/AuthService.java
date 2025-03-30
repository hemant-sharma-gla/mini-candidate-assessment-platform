package com.assessment.service.interfaces;

import com.assessment.dto.AuthResponse;
import com.assessment.dto.LoginRequest;
import com.assessment.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The interface Auth service.
 */
public interface AuthService {
    /**
     * Register auth response.
     *
     * @param request the request
     * @return the auth response
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Login auth response.
     *
     * @param request the request
     * @return the auth response
     */
    AuthResponse login(LoginRequest request);

    /**
     * Logout.
     *
     * @param request the request
     */
    void logout(HttpServletRequest request);
}

package com.assessment.filter;

import com.assessment.security.JwtService;
import com.assessment.service.interfaces.TokenBlackService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.assessment.constants.ApplicationConstants.AUTHORIZATION;
import static com.assessment.constants.ApplicationConstants.BEARER_SPACE;

/**
 * A filter that implements JWT-based authentication for the application.
 * This filter intercepts incoming HTTP requests to validate JWT tokens and set up
 * Spring Security authentication context. It processes all requests that start with "/api"
 * except those containing "auth" in their path.
 * <p>
 * The filter performs the following operations:
 * 1. Extracts JWT token from the Authorization header
 * 2. Validates the token against the blacklist
 * 3. Extracts user information from the token
 * 4. Loads user details from the UserDetailsService
 * 5. Validates the token against the user details
 * 6. Sets up the security context if validation is successful
 * <p>
 * If any step fails, it returns an appropriate error response with HTTP 401 Unauthorized.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Service responsible for JWT token operations including generation, validation, and extraction of claims.
     * Handles all JWT-related cryptographic operations and token management.
     */
    private final JwtService jwtService;

    /**
     * Service responsible for loading user details from the database.
     * Used to retrieve user information for authentication and authorization purposes.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Service responsible for managing blacklisted tokens.
     * Used to check if a token has been invalidated or revoked.
     */
    private final TokenBlackService tokenBlackService;

    /**
     * Filters incoming requests to validate JWT tokens and set up authentication.
     * This method processes all requests that start with "/api" except those containing "auth".
     * <p>
     * The method performs the following steps:
     * 1. Checks if the request URI requires authentication
     * 2. Extracts the JWT token from the Authorization header
     * 3. Validates the token against the blacklist
     * 4. Extracts username from the token
     * 5. Loads user details from the UserDetailsService
     * 6. Validates the token against the user details
     * 7. Sets up the security context if validation is successful
     * <p>
     * If any step fails, it logs the error and returns an appropriate error response.
     *
     * @param request     the incoming HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to execute
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api") && !request.getRequestURI().contains("auth")) {
            try {
                String jwt = getJwtFromRequest(request);
                if (jwt != null && !tokenBlackService.isTokenBlacklisted(jwt)) {
                    String username = jwtService.extractUsername(jwt);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception ex) {
                log.error("Could not set user authentication in security context", ex);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the request.
     * The token should be in the format "Bearer <token>".
     *
     * @param request the HTTP request containing the Authorization header
     * @return the JWT token if present and properly formatted, null otherwise
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER_SPACE)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
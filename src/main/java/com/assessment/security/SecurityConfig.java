package com.assessment.security;

import com.assessment.filter.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.assessment.constants.ApplicationConstants.ADMIN;
import static com.assessment.constants.ApplicationConstants.ALLOW_ALL_ORIGIN;
import static com.assessment.constants.ApplicationConstants.API_ADMIN_BASE;
import static com.assessment.constants.ApplicationConstants.API_AUTH_BASE;
import static com.assessment.constants.ApplicationConstants.API_CANDIDATE_BASE;
import static com.assessment.constants.ApplicationConstants.API_SWAGGER_BASE;
import static com.assessment.constants.ApplicationConstants.AUTHORIZATION;
import static com.assessment.constants.ApplicationConstants.CONTENT_TYPE;
import static com.assessment.constants.ApplicationConstants.CORS_MAX_AGE;
import static com.assessment.constants.ApplicationConstants.CORS_PATTERN;
import static com.assessment.constants.ApplicationConstants.DELETE;
import static com.assessment.constants.ApplicationConstants.GET;
import static com.assessment.constants.ApplicationConstants.OPTIONS;
import static com.assessment.constants.ApplicationConstants.POST;
import static com.assessment.constants.ApplicationConstants.PUT;
import static com.assessment.constants.ApplicationConstants.USER;
import static com.assessment.constants.ApplicationConstants.X_REQUESTED_WITH;

/**
 * Security configuration class that defines the security rules and filters for the application.
 * This class configures JWT-based authentication, CORS, CSRF, and endpoint authorization rules.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    /**
     * The JWT authentication filter that processes JWT tokens for authentication.
     * This filter intercepts incoming requests to validate JWT tokens and set up authentication.
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * The service responsible for loading user-specific data during authentication.
     * This service is used by the authentication provider to retrieve user details
     * such as username, password, and authorities.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Constructs a new SecurityConfig with the specified dependencies.
     *
     * @param jwtAuthFilter      the JWT authentication filter to be used
     * @param userDetailsService the service to load user-specific data
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the security filter chain for the application.
     * This includes:
     * - Disabling CSRF for stateless REST API
     * - Configuring CORS
     * - Setting up stateless session management
     * - Defining public endpoints that don't require authentication
     * - Setting up role-based access control for protected endpoints
     * - Adding the JWT authentication filter
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cq -> {
                    try {
                        cq.disable().cors(c -> corsConfigurationSource());
                    } catch (Exception e) {
                        log.error("{0}", e);
                    }
                }).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(API_AUTH_BASE).permitAll()
                        .requestMatchers(API_ADMIN_BASE).hasRole(ADMIN)
                        .requestMatchers(API_CANDIDATE_BASE).hasAnyRole(USER, ADMIN)
                        .requestMatchers(API_SWAGGER_BASE).permitAll()
                        .anyRequest().permitAll()
                ).sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configures CORS settings for the application.
     *
     * @return the CORS configuration source
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ALLOW_ALL_ORIGIN));
        configuration.setAllowedMethods(Arrays.asList(GET, POST, PUT, DELETE, OPTIONS));
        configuration.setAllowedHeaders(Arrays.asList(AUTHORIZATION, CONTENT_TYPE, X_REQUESTED_WITH));
        configuration.setExposedHeaders(List.of(AUTHORIZATION));
        configuration.setMaxAge(CORS_MAX_AGE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_PATTERN, configuration);
        return source;
    }

    /**
     * Configures the authentication provider bean.
     * This sets up the UserDetailsService and PasswordEncoder for authentication.
     *
     * @return the configured AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configures the authentication manager bean.
     *
     * @param config the authentication configuration
     * @return the configured AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures the password encoder bean.
     *
     * @return the configured PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

} 
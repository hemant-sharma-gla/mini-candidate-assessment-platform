package com.assessment.service.impl;

import com.assessment.constants.ErrorConstants;
import com.assessment.entity.User;
import com.assessment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * This service is responsible for loading user-specific data during authentication.
 * It converts our application's User entity into Spring Security's UserDetails format.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    /**
     * Repository for accessing user data from the database
     */
    private final UserRepository userRepository;

    /**
     * Loads a user by their username.
     * This method:
     * 1. Retrieves the user from the database
     * 2. Converts the user's roles to Spring Security authorities
     * 3. Creates a UserDetails object with the user's information
     *
     * @param username the username to look up
     * @return UserDetails containing the user's information and authorities
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
} 
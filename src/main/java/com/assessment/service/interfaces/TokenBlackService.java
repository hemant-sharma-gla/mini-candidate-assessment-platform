package com.assessment.service.interfaces;

/**
 * The interface Token black service.
 */
public interface TokenBlackService {
    /**
     * Blacklist token.
     *
     * @param token the token
     */
    void blacklistToken(String token);

    /**
     * Is token blacklisted boolean.
     *
     * @param token the token
     * @return the boolean
     */
    boolean isTokenBlacklisted(String token);
}

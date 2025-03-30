package com.assessment.service.impl;

import com.assessment.service.interfaces.TokenBlackService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service responsible for managing blacklisted JWT tokens.
 * This service maintains a thread-safe collection of blacklisted tokens and their expiration times.
 * It provides functionality to blacklist tokens, check if a token is blacklisted, and automatically
 * clean up expired tokens on a scheduled basis.
 */
@Service
class TokenBlacklistServiceImpl implements TokenBlackService {

    /**
     * Thread-safe map that stores blacklisted tokens and their expiration times.
     * The key is the JWT token string, and the value is the Instant when the token expires.
     */
    private final Map<String, Instant> blacklistedTokens = new ConcurrentHashMap<>();

    /**
     * Duration in hours for which a token remains blacklisted after being added.
     * This constant determines the lifetime of a blacklisted token in the system.
     */
    private static final long TOKEN_BLACKLIST_DURATION_HOURS = 24;

    /**
     * Adds a token to the blacklist with an expiration time.
     * The token will remain blacklisted for the duration specified by TOKEN_BLACKLIST_DURATION_HOURS.
     *
     * @param token The JWT token to be blacklisted
     */
    @Override
    public void blacklistToken(String token) {
        blacklistedTokens.put(token, Instant.now().plusSeconds(TOKEN_BLACKLIST_DURATION_HOURS * 3600));
    }

    /**
     * Checks if a token is currently blacklisted.
     * If the token is found in the blacklist but has expired, it will be automatically removed
     * from the blacklist and the method will return false.
     *
     * @param token The JWT token to check
     * @return true if the token is blacklisted and not expired, false otherwise
     */
    @Override
    public boolean isTokenBlacklisted(String token) {
        Instant expirationTime = blacklistedTokens.get(token);
        if (expirationTime == null) {
            return false;
        }
        if (Instant.now().isAfter(expirationTime)) {
            blacklistedTokens.remove(token);
            return false;
        }
        return true;
    }

    /**
     * Scheduled task that runs every hour to clean up expired tokens from the blacklist.
     * This method automatically removes any tokens whose expiration time has passed.
     * The cleanup is performed using a thread-safe operation on the ConcurrentHashMap.
     */
    @Scheduled(fixedRate = 3600000)
    public void cleanupExpiredTokens() {
        blacklistedTokens.entrySet().removeIf(entry ->
                Instant.now().isAfter(entry.getValue()));
    }
}
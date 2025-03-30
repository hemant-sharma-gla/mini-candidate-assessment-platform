package com.assessment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.assessment.constants.ApplicationConstants.JWT_EXPIRATION;
import static com.assessment.constants.ApplicationConstants.JWT_SECRET_KEY;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 * This class provides functionality for generating, validating, and parsing JWTs.
 * It uses the HMAC-SHA256 algorithm for signing tokens and supports custom claims.
 * <p>
 * The service is configured using two properties:
 * - jwt.secret: The secret key used for signing tokens
 * - jwt.expiration: The expiration time for tokens in milliseconds
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see io.jsonwebtoken.Jwts
 */
@Service
public class JwtService {


    /**
     * Extracts the username from a JWT token.
     * The username is stored in the token's subject claim.
     *
     * @param token the JWT token to extract the username from
     * @return the username stored in the token's subject claim
     * @throws io.jsonwebtoken.JwtException if the token is invalid or malformed
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a JWT token for a user with no additional claims.
     * The token includes the following standard claims:
     * - subject: the username
     * - issuedAt: current timestamp
     * - expiration: current timestamp + jwtExpiration
     *
     * @param userDetails the user details containing the username
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional custom claims.
     * The token includes both the standard claims and any additional claims provided.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details containing the username
     * @return the generated JWT token containing all claims
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates if a token is valid for the given user details.
     * A token is considered valid if:
     * 1. The username in the token matches the username in userDetails
     * 2. The token has not expired
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Extracts a specific claim from the token using a claims resolver function.
     * This is a generic method that allows extracting any claim type from the token.
     *
     * @param <T>            the type of the claim to extract
     * @param token          the JWT token
     * @param claimsResolver function to extract the desired claim
     * @return the extracted claim of type T
     * @throws io.jsonwebtoken.JwtException if the token is invalid or malformed
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a token.
     * This method parses the token and returns all claims contained within it.
     *
     * @param token the JWT token
     * @return all claims stored in the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or malformed
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if a token has expired by comparing its expiration date with the current time.
     *
     * @param token the JWT token to check
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a token.
     * The expiration date is stored in the token's expiration claim.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Gets the signing key used for JWT operations.
     * The key is generated from the secret key using HMAC-SHA256 algorithm.
     *
     * @return the signing key for JWT operations
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
    }
} 
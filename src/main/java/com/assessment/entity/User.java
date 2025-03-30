package com.assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a user in the system.
 * This class implements Spring Security's UserDetails interface to provide user authentication and authorization.
 * It stores user authentication credentials, profile information, and assessment-related data.
 * <p>
 * The class uses JPA annotations for persistence and validation constraints for data integrity.
 * It supports multiple roles per user through a many-to-many relationship with the Role entity.
 *
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see jakarta.persistence.Entity
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {
    /**
     * Unique identifier for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username for authentication
     * Must be unique and between 3 and 50 characters
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * User's email address
     * Must be unique and in valid email format
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * User's password
     * Must be at least 6 characters long
     * Stored in encrypted format
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;

    /**
     * Flag indicating if the user account is enabled
     */
    private boolean enabled = true;

    /**
     * Set of roles assigned to the user
     * Fetched eagerly to avoid lazy loading issues
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /**
     * Score achieved by the user in the assessment
     * Null if the assessment hasn't been completed
     */
    private Integer quizScore;

    /**
     * Timestamp when the user submitted the assessment
     * Null if the assessment hasn't been completed
     */
    private LocalDateTime quizSubmissionTime;

    /**
     * Returns the authorities granted to the user.
     * This method converts the user's roles into Spring Security GrantedAuthority objects.
     *
     * @return Collection of GrantedAuthority objects representing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    /**
     * Indicates whether the user's account has expired.
     * This implementation always returns true as account expiration is not implemented.
     *
     * @return true if the user's account is valid (not expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * This implementation always returns true as account locking is not implemented.
     *
     * @return true if the user is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     * This implementation always returns true as credential expiration is not implemented.
     *
     * @return true if the user's credentials are valid (not expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * A disabled user cannot be authenticated.
     *
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
} 
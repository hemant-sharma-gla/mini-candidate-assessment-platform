package com.assessment.init;

import com.assessment.entity.Role;
import com.assessment.entity.User;
import com.assessment.exception.ResourceNotFoundException;
import com.assessment.repository.RoleRepository;
import com.assessment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.assessment.constants.ApplicationConstants.ADMINISTRATOR_ROLE;
import static com.assessment.constants.ApplicationConstants.ADMIN_DEFAULT_MAIL;
import static com.assessment.constants.ApplicationConstants.ADMIN_DEFAULT_PASSWORD;
import static com.assessment.constants.ApplicationConstants.ADMIN_LOWERCASE;
import static com.assessment.constants.ApplicationConstants.ROLE_ADMIN;
import static com.assessment.constants.ApplicationConstants.ROLE_USER;
import static com.assessment.constants.ApplicationConstants.USER_ROLE;
import static com.assessment.constants.ErrorConstants.ADMIN_ROLE_NOT_FOUND;

/**
 * DatabaseInitializer is responsible for initializing the database with default data
 * when the application starts. It creates default roles and an admin user if they don't exist.
 * This class implements CommandLineRunner to ensure it runs after the application context is loaded.
 *
 * <p>The initializer performs the following tasks:
 * <ul>
 *   <li>Creates default roles (ROLE_ADMIN and ROLE_USER) if they don't exist</li>
 *   <li>Creates a default admin user with ROLE_ADMIN if it doesn't exist</li>
 *   <li>Uses password encryption for secure storage of user credentials</li>
 * </ul>
 *
 * <p>This component is automatically detected and executed by Spring Boot during application startup.
 * It ensures that the application has the necessary base data for proper functioning.
 *
 * @see CommandLineRunner
 * @see Role
 * @see User
 */
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    /**
     * Repository for managing Role entities in the database.
     * Used to create and retrieve role information during initialization.
     */
    private final RoleRepository roleRepository;

    /**
     * Repository for managing User entities in the database.
     * Used to create and retrieve user information during initialization.
     */
    private final UserRepository userRepository;

    /**
     * Password encoder for securely hashing user passwords.
     * Used to encrypt passwords before storing them in the database.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the database initialization process when the application starts.
     * Creates default roles (ROLE_ADMIN and ROLE_USER) and an admin user if they don't exist.
     *
     * <p>The initialization process includes:
     * <ul>
     *   <li>Creating the administrator role if it doesn't exist</li>
     *   <li>Creating the user role if it doesn't exist</li>
     *   <li>Creating a default admin user with the following credentials:
     *       <ul>
     *         <li>Username: admin</li>
     *         <li>Email: admin@example.com</li>
     *         <li>Password: admin123 (encrypted)</li>
     *       </ul>
     *   </li>
     * </ul>
     *
     * @param args Command line arguments passed to the application (not used in this implementation)
     * @throws RuntimeException if the admin role cannot be found during admin user creation
     */
    @Override
    public void run(String... args) {
        createRoleIfNotExists(ROLE_ADMIN, ADMINISTRATOR_ROLE);
        createRoleIfNotExists(ROLE_USER, USER_ROLE);

        if (!userRepository.existsByUsername(ADMIN_LOWERCASE)) {
            User adminUser = new User();
            adminUser.setUsername(ADMIN_LOWERCASE);
            adminUser.setEmail(ADMIN_DEFAULT_MAIL);
            adminUser.setPassword(passwordEncoder.encode(ADMIN_DEFAULT_PASSWORD));
            adminUser.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(ROLE_ADMIN)
                    .orElseThrow(() -> new ResourceNotFoundException(ADMIN_ROLE_NOT_FOUND)));
            adminUser.setRoles(roles);
            userRepository.save(adminUser);
        }
    }

    /**
     * Creates a new role in the database if it doesn't already exist.
     * This method is used to ensure that essential roles are present in the system.
     *
     * <p>The method performs the following checks:
     * <ul>
     *   <li>Verifies if the role already exists by name</li>
     *   <li>Creates a new role only if it doesn't exist</li>
     *   <li>Sets both the role name and description</li>
     * </ul>
     *
     * @param name        The name of the role to create (e.g., "ROLE_ADMIN")
     * @param description A descriptive text explaining the purpose and responsibilities of the role
     */
    private void createRoleIfNotExists(String name, String description) {
        if (!roleRepository.existsByName(name)) {
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
        }
    }
} 
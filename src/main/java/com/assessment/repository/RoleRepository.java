package com.assessment.repository;

import com.assessment.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity.
 * Provides methods for CRUD operations and custom queries for user roles.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to search for
     * @return an Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Checks if a role exists with the given name.
     *
     * @param name the name of the role to check
     * @return true if a role exists with this name, false otherwise
     */
    boolean existsByName(String name);
} 
package com.assessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a user role in the system.
 * This class is used to define different roles that users can have,
 * such as ADMIN, USER, etc.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    /**
     * Unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the role (e.g., "ADMIN", "USER").
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * A description of the role and its purpose.
     */
    private String description;
} 
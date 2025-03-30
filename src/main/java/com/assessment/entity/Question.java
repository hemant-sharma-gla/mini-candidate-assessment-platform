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
 * Entity class representing an assessment question.
 * This class stores the question details including options and correct answer.
 */
@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
public class Question {
    /**
     * Unique identifier for the question
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The actual question text
     */
    @Column(nullable = false)
    private String question;

    /**
     * First option for the question
     */
    @Column(nullable = false)
    private String optionA;

    /**
     * Second option for the question
     */
    @Column(nullable = false)
    private String optionB;

    /**
     * Third option for the question
     */
    @Column(nullable = false)
    private String optionC;

    /**
     * Fourth option for the question
     */
    @Column(nullable = false)
    private String optionD;

    /**
     * The correct answer for the question
     * Must match one of the options (A, B, C, or D)
     */
    @Column(nullable = false)
    private String correctAnswer;
} 
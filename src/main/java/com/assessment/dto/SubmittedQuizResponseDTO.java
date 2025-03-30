package com.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) that represents the response after submitting a quiz.
 * This class contains information about the quiz submission including the score and submission time.
 *
 * @author Assessment System
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedQuizResponseDTO {
    /**
     * The score achieved in the quiz.
     * Represents the number of correct answers or points earned.
     */
    private Integer score;

    /**
     * The timestamp when the quiz was submitted.
     * Format: ISO 8601 datetime string (e.g., "2024-03-20T15:30:00Z")
     */
    private String quizSubmissionTime;
} 
package com.assessment.dto;

import lombok.Data;

/**
 * Data Transfer Object for a single quiz answer.
 * Contains the question ID and the selected answer.
 */
@Data
public class QuizAnswerDTO {
    /**
     * ID of the question being answered
     */
    private Long questionId;

    /**
     * Selected answer (A, B, C, or D)
     */
    private String selectedAnswer;
} 
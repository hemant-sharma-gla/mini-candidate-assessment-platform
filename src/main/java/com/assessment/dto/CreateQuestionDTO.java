package com.assessment.dto;

import lombok.Data;

/**
 * Data Transfer Object for question creation and updates.
 * Contains all the necessary fields to create or modify a question.
 */
@Data
public class CreateQuestionDTO {

    /**
     * The actual question text
     */
    private String question;

    /**
     * First option for the question
     */
    private String optionA;

    /**
     * Second option for the question
     */
    private String optionB;

    /**
     * Third option for the question
     */
    private String optionC;

    /**
     * Fourth option for the question
     */
    private String optionD;

    /**
     * The correct answer for the question
     * Must be one of: A, B, C, or D
     */
    private String correctAnswer;
} 
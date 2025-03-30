package com.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for question creation and updates.
 * Contains all the necessary fields to create or modify a question.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {

    /**
     * The question id
     */
    private Long id;

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

} 
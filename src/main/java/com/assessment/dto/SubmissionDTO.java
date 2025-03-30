package com.assessment.dto;

import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object for quiz submissions.
 * Contains the user's email and their answers to the quiz questions.
 */
@Data
public class SubmissionDTO {
    /**
     * List of answers for each question
     */
    private List<QuizAnswerDTO> answers;
} 
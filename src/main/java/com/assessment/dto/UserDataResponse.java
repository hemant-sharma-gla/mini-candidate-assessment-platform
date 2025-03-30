package com.assessment.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing the response containing user's assessment data.
 * This class holds information about the user's score and when they submitted their assessment.
 */
@Data
public class UserDataResponse {
    /**
     * The numerical score achieved by the user in the assessment.
     */
    private Integer score;

    /**
     * The timestamp when the user submitted their assessment.
     */
    private LocalDateTime submissionTime;
}

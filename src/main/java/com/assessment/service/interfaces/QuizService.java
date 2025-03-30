package com.assessment.service.interfaces;

import com.assessment.dto.SubmissionDTO;
import com.assessment.dto.SubmittedQuizResponseDTO;

/**
 * The interface Quiz service.
 */
public interface QuizService {
    /**
     * Submit quiz user.
     *
     * @param submission the submission
     * @return the user
     */
    SubmittedQuizResponseDTO submitQuiz(SubmissionDTO submission);

    /**
     * Gets user result.
     *
     * @param userId the user id
     * @return the user result
     */
    SubmittedQuizResponseDTO getUserResult(Long userId);
}

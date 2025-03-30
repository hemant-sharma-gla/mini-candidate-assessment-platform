package com.assessment.service.interfaces;

import com.assessment.dto.CreateQuestionDTO;
import com.assessment.dto.QuestionDTO;
import com.assessment.dto.QuestionResponseDTO;

import java.util.List;

/**
 * The interface Question service.
 */
public interface QuestionService {

    /**
     * Create question question dto.
     *
     * @param createQuestionDTO the question dto
     * @return the question dto
     */
    QuestionDTO createQuestion(CreateQuestionDTO createQuestionDTO);

    /**
     * Gets all questions.
     *
     * @return the all questions
     */
    List<QuestionDTO> getAllQuestions();

    /**
     * Gets random questions.
     *
     * @param count the count
     * @return the random questions
     */
    List<QuestionResponseDTO> getRandomQuestions(int count);
}

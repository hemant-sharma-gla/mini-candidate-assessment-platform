package com.assessment.service.impl;

import com.assessment.dto.QuizAnswerDTO;
import com.assessment.dto.SubmissionDTO;
import com.assessment.dto.SubmittedQuizResponseDTO;
import com.assessment.entity.Question;
import com.assessment.entity.User;
import com.assessment.exception.ResourceNotFoundException;
import com.assessment.exception.UserValidationException;
import com.assessment.repository.QuestionRepository;
import com.assessment.repository.UserRepository;
import com.assessment.service.interfaces.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.assessment.constants.ApplicationConstants.ROLE_ADMIN;
import static com.assessment.constants.ErrorConstants.ADMIN_CANNOT_SUBMIT_QUIZ;
import static com.assessment.constants.ErrorConstants.QUESTION_NOT_FOUND;
import static com.assessment.constants.ErrorConstants.USER_ALREADY_SUBMITTED_QUIZ;
import static com.assessment.constants.ErrorConstants.USER_NOT_FOUND;

/**
 * Service class for managing quiz submissions and scoring.
 * This service handles quiz submissions and score calculations for users.
 * It provides functionality for:
 * <ul>
 *   <li>Submitting quiz answers</li>
 *   <li>Calculating quiz scores</li>
 *   <li>Retrieving quiz results</li>
 * </ul>
 */
@Service
@Transactional
@RequiredArgsConstructor
class QuizServiceImpl implements QuizService {

    /**
     * Repository for managing user data and quiz-related operations
     */
    private final UserRepository userRepository;

    /**
     * Repository for managing quiz questions and their answers
     */
    private final QuestionRepository questionRepository;

    /**
     * Processes a quiz submission from a user.
     * This method:
     * 1. Validates the user exists
     * 2. Calculates the score based on correct answers
     * 3. Updates the user's record with the score and submission time
     *
     * @param submission the submission containing user email and answers
     * @return the updated user with their score
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public SubmittedQuizResponseDTO submitQuiz(SubmissionDTO submission) {
        Collection<SimpleGrantedAuthority> roles = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        if (roles.stream().anyMatch(r -> ROLE_ADMIN.equalsIgnoreCase(r.getAuthority()))) {
            throw new ResourceNotFoundException(ADMIN_CANNOT_SUBMIT_QUIZ);
        }
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = details.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        if (user.getQuizScore() != null) {
            throw new UserValidationException(USER_ALREADY_SUBMITTED_QUIZ);
        }
        int score = calculateScore(submission.getAnswers());
        user.setQuizScore(score);
        user.setQuizSubmissionTime(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return new SubmittedQuizResponseDTO(savedUser.getQuizScore(), String.valueOf(savedUser.getQuizSubmissionTime()));
    }

    /**
     * Calculates the score for a set of answers.
     * Each correct answer is worth one point.
     *
     * @param answers list of answers for each question
     * @return the total score
     */
    private int calculateScore(List<QuizAnswerDTO> answers) {
        int score = 0;
        for (QuizAnswerDTO answer : answers) {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND));
            if (question.getCorrectAnswer().equals(answer.getSelectedAnswer())) {
                score++;
            }
        }
        return score;
    }

    /**
     * Retrieves the quiz result for a specific user.
     *
     * @param userId the ID of the user
     * @return the user with their quiz result
     * @throws ResourceNotFoundException if the user is not found
     */
    @Override
    public SubmittedQuizResponseDTO getUserResult(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        SubmittedQuizResponseDTO userDataResponse = new SubmittedQuizResponseDTO();
        userDataResponse.setScore(user.getQuizScore());
        userDataResponse.setQuizSubmissionTime(String.valueOf(user.getQuizSubmissionTime()));
        return userDataResponse;
    }
} 
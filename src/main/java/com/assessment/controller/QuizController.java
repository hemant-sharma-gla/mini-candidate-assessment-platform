package com.assessment.controller;

import com.assessment.dto.QuestionResponseDTO;
import com.assessment.dto.SubmissionDTO;
import com.assessment.dto.SubmittedQuizResponseDTO;
import com.assessment.service.interfaces.QuestionService;
import com.assessment.service.interfaces.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.assessment.constants.ApplicationConstants.DEFAULT_QUIZ_QUESTIONS;

/**
 * REST Controller for managing quiz-related operations.
 * This controller handles quiz questions retrieval, submission, and result retrieval.
 * All endpoints are secured and require appropriate user roles for access.
 *
 * @author Assessment System
 * @version 1.0
 */
@RestController
@RequestMapping("/api/quiz")
@Tag(name = "Quiz", description = "APIs for Quiz Management")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    /**
     * Service responsible for handling quiz-related operations such as submission and result retrieval.
     * Provides functionality for managing quiz submissions and calculating scores.
     */
    private final QuestionService questionService;

    /**
     * Service responsible for managing question-related operations.
     * Handles question retrieval, randomization, and management of question data.
     */

    /**
     * Retrieves a random set of questions for the assessment.
     * This endpoint is accessible to both users and administrators.
     *
     * @return ResponseEntity containing a list of randomly selected questions
     * @throws RuntimeException if no questions are available in the database
     */
    @Operation(
            summary = "Get Quiz Questions",
            description = "Retrieves a random set of questions for the assessment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved questions",
                    content = @Content(schema = @Schema(implementation = QuestionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No questions available")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<QuestionResponseDTO>> getQuiz() {
        List<QuestionResponseDTO> questions = questionService.getRandomQuestions(DEFAULT_QUIZ_QUESTIONS);
        return ResponseEntity.ok(questions);
    }

    /**
     * Submits a user's quiz answers and calculates their score.
     * This endpoint is only accessible to users with the USER role.
     *
     * @param submission The submission data containing user answers and identification
     * @return ResponseEntity containing the updated user object with their quiz results
     * @throws IllegalArgumentException if the submission data is invalid
     * @throws RuntimeException         if the user is not found
     */
    @Operation(
            summary = "Submit Quiz",
            description = "Submits the user's answers and calculates the score"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quiz submitted successfully",
                    content = @Content(schema = @Schema(implementation = SubmittedQuizResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid submission data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SubmittedQuizResponseDTO> submitQuiz(
            @Parameter(description = "Quiz submission details", required = true)
            @RequestBody SubmissionDTO submission) {
        SubmittedQuizResponseDTO updatedUser = quizService.submitQuiz(submission);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Retrieves the assessment result for a specific user.
     * This endpoint is accessible to both users and administrators.
     *
     * @param userId The unique identifier of the user whose results are to be retrieved
     * @return ResponseEntity containing the user object with their assessment results
     * @throws RuntimeException if the user is not found
     */
    @Operation(
            summary = "Get User Result",
            description = "Retrieves the assessment result for a specific user"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved result",
                    content = @Content(schema = @Schema(implementation = SubmittedQuizResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/report/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SubmittedQuizResponseDTO> getResult(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId) {
        SubmittedQuizResponseDTO userResult = quizService.getUserResult(userId);
        return ResponseEntity.ok(userResult);
    }
} 
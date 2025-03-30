package com.assessment.controller;

import com.assessment.dto.CreateQuestionDTO;
import com.assessment.dto.QuestionDTO;
import com.assessment.service.interfaces.QuestionService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for handling administrative operations related to questions in the assessment system.
 * This controller provides endpoints for managing questions and is restricted to users with ADMIN role.
 *
 * @see QuestionService
 * @see QuestionDTO
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "APIs for Question Management")
@RequiredArgsConstructor
public class AdminController {

    /**
     * Service for managing questions in the assessment system.
     * Handles operations such as creating and retrieving questions.
     */
    private final QuestionService questionService;

    /**
     * Creates a new question in the assessment system.
     * This endpoint is restricted to users with ADMIN role.
     *
     * @param createQuestionDTO The question details to be created
     * @return ResponseEntity containing the created question
     * @throws IllegalArgumentException if the question data is invalid
     * @throws SecurityException        if the user is not authenticated or not authorized
     */
    @Operation(
            summary = "Create Question",
            description = "Creates a new question in the assessment system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Question created successfully",
                    content = @Content(schema = @Schema(implementation = CreateQuestionDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid question data"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @PostMapping("/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionDTO> createQuestion(
            @Parameter(description = "Question details", required = true)
            @RequestBody CreateQuestionDTO createQuestionDTO) {
        QuestionDTO createdQuestion = questionService.createQuestion(createQuestionDTO);
        return ResponseEntity.ok(createdQuestion);
    }

    /**
     * Retrieves all questions from the assessment system.
     * This endpoint is restricted to users with ADMIN role.
     *
     * @return ResponseEntity containing a list of all questions
     * @throws SecurityException if the user is not authenticated or not authorized
     */
    @Operation(
            summary = "Get All Questions",
            description = "Retrieves all questions from the assessment system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved questions",
                    content = @Content(schema = @Schema(implementation = QuestionDTO.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized")
    })
    @GetMapping("/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
} 
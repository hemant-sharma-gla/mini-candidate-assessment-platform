package com.assessment.service.impl;

import com.assessment.dto.CreateQuestionDTO;
import com.assessment.dto.QuestionDTO;
import com.assessment.dto.QuestionResponseDTO;
import com.assessment.entity.Question;
import com.assessment.repository.QuestionRepository;
import com.assessment.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing assessment questions.
 * This service handles CRUD operations for questions and their conversion between DTOs and entities.
 */
@Service
@Transactional
@RequiredArgsConstructor
class QuestionServiceImpl implements QuestionService {

    /**
     * Repository for managing Question entities in the database.
     * Handles all database operations related to questions including CRUD operations
     * and custom queries for retrieving random questions.
     */
    private final QuestionRepository questionRepository;

    /**
     * Creates a new question in the system.
     *
     * @param createQuestionDTO the question data to create
     * @return the created question as a DTO
     */
    @Override
    public QuestionDTO createQuestion(CreateQuestionDTO createQuestionDTO) {
        Question question = convertToEntity(createQuestionDTO);
        Question savedQuestion = questionRepository.save(question);
        return convertToDTO(savedQuestion);
    }

    /**
     * Retrieves all questions in the system.
     *
     * @return list of all questions as DTOs
     */
    @Override
    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Retrieves a random set of questions.
     *
     * @param count the number of questions to retrieve
     * @return a list of random questions
     */
    @Override
    public List<QuestionResponseDTO> getRandomQuestions(int count) {
        List<Question> questions = questionRepository.findRandomQuestions(count);
        return questions.stream().limit(count)
                .map(question -> new QuestionResponseDTO(question.getId(), question.getQuestion(),
                        question.getOptionA(), question.getOptionB(), question.getOptionC(),
                        question.getOptionD())).toList();
    }

    /**
     * Converts a Question entity to a QuestionDTO.
     *
     * @param question the Question entity to convert
     * @return the converted QuestionDTO
     */
    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestion(question.getQuestion());
        dto.setOptionA(question.getOptionA());
        dto.setOptionB(question.getOptionB());
        dto.setOptionC(question.getOptionC());
        dto.setOptionD(question.getOptionD());
        dto.setCorrectAnswer(question.getCorrectAnswer());
        return dto;
    }

    /**
     * Converts a QuestionDTO to a Question entity.
     *
     * @param createQuestionDTO the QuestionDTO to convert
     * @return the converted Question entity
     */
    private Question convertToEntity(CreateQuestionDTO createQuestionDTO) {
        Question question = new Question();
        question.setQuestion(createQuestionDTO.getQuestion());
        question.setOptionA(createQuestionDTO.getOptionA());
        question.setOptionB(createQuestionDTO.getOptionB());
        question.setOptionC(createQuestionDTO.getOptionC());
        question.setOptionD(createQuestionDTO.getOptionD());
        question.setCorrectAnswer(createQuestionDTO.getCorrectAnswer());
        return question;
    }
} 
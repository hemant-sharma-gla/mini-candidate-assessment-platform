package com.assessment.repository;

import com.assessment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Question entity.
 * Provides methods for CRUD operations and custom queries for assessment questions.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Retrieves a random set of questions.
     * Uses JPQL's RANDOM() function for randomization.
     *
     * @param count the number of questions to retrieve
     * @return a list of random questions
     */
    @Query(value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT :count", nativeQuery = true)
    List<Question> findRandomQuestions(int count);
} 
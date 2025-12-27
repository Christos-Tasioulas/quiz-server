package com.example.quiz.repositories;

import com.example.quiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestionAndQuizId(String question, Long quizId);

    List<Question> findQuestionsByQuizId(Long quizId);

    @Query(value = "SELECT * FROM questions WHERE quiz_id = :quizId ORDER BY random() LIMIT 1", nativeQuery = true)
    Question findRandomQuestionByQuizId(Long quizId);
}

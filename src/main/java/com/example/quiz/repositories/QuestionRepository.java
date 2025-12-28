package com.example.quiz.repositories;

import com.example.quiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestionAndQuizId(String question, Long quizId);

    List<Question> findQuestionsByQuizId(Long quizId);

    // Optional: fetch random question for a quiz
    @Query("SELECT q FROM Question q WHERE q.quiz.id = :quizId ORDER BY function('RANDOM')")
    Question findRandomQuestionByQuizId(@Param("quizId") Long quizId);
}

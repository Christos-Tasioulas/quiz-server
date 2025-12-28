package com.example.quiz.repositories;

import com.example.quiz.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByAnswer(String answer);

    List<Answer> findByQuestionId(Long questionId);

    @Query(value = "SELECT score FROM answers WHERE id = :id", nativeQuery = true)
    Object getScoreById(Long id);
}

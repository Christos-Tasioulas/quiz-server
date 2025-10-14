package com.example.quiz.repositories;

import com.example.quiz.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    boolean existsByAnswer(String answer);
}

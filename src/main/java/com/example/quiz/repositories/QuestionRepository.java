package com.example.quiz.repositories;

import com.example.quiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestion(String question);
}

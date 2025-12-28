package com.example.quiz.repositories;

import com.example.quiz.entities.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsByName(String name);

    Optional<Quiz> findByName(String name);

    // Optional: fetch with questions eagerly if needed
    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Optional<Quiz> findWithQuestionsById(Long id);
}

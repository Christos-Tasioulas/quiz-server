package com.example.quiz.repositories;

import com.example.quiz.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    boolean existsByName(String name);

    Optional<Quiz> findByName(String name);
}

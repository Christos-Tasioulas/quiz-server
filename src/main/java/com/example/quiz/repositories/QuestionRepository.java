package com.example.quiz.repositories;

import com.example.quiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestion(String question);

    @Query(value = "SELECT * FROM questions ORDER BY random() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();
}

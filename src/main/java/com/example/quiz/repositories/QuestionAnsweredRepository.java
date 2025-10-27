package com.example.quiz.repositories;

import com.example.quiz.entities.QuestionAnswered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionAnsweredRepository extends JpaRepository<QuestionAnswered, Long> {
    List<QuestionAnswered> findByRunId(Long runId);
}

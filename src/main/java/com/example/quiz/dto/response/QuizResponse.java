package com.example.quiz.dto.response;

import com.example.quiz.entities.Quiz;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuizResponse(
        @NotNull Long id,
        @NotNull String name,
        List<QuestionResponse> questions,
        List<RunResponse> runs,
        @NotNull int numberOfQuestions
) {
    public QuizResponse(Quiz quiz) {
        this(
                quiz.getId(),
                quiz.getName(),
                quiz.getQuestions().stream().map(QuestionResponse::new).toList(),
                quiz.getRuns().stream().map(RunResponse::new).toList(),
                quiz.getNumberOfQuestions()
        );
    }
}

package com.example.quiz.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@JsonInclude
public record RunRequest(
        @NotNull Long userId,
        @NotNull Long quizId,
        Map<String, Object> score,
        @NotNull int totalQuestions,
        Integer questionsAnswered
) {
}

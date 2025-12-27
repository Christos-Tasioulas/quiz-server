package com.example.quiz.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuizRequest(
        @NotNull String name,
        List<QuestionRequest> questions
) {
}

package com.example.quiz.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record AnswerRequest(
        Long id,
        @NotBlank String answer,
        Map<String, Object> score
) {
}

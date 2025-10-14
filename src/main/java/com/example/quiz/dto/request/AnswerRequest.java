package com.example.quiz.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AnswerRequest(
        @NotBlank String answer
) {
}

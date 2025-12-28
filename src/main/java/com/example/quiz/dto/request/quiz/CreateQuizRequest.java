package com.example.quiz.dto.request.quiz;

import jakarta.validation.constraints.NotBlank;

public record CreateQuizRequest(
        @NotBlank String name
) {}


package com.example.quiz.dto.request;

import jakarta.validation.constraints.NotNull;

public record QuestionAnsweredRequest (
    @NotNull Long runId,
    @NotNull Long questionId,
    Long answerId,
    boolean questionAnswered
) {}

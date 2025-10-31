package com.example.quiz.dto.response;

import com.example.quiz.entities.QuestionAnswered;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionAnsweredResponse(
        @NotNull Long id,
        @NotNull Long runId,
        @NotNull Long questionId,
        @NotBlank String questionText,
        Long answerId,
        String answerText,
        boolean questionAnswered
) {
    public QuestionAnsweredResponse(QuestionAnswered qa) {
        this(
                qa.getId(),
                qa.getRun().getId(),
                qa.getQuestion().getId(),
                qa.getQuestion().getQuestion(),
                qa.getAnswer() != null ? qa.getAnswer().getId() : null,
                qa.getAnswer() != null ? qa.getAnswer().getAnswer() : null,
                qa.isQuestionAnswered()
        );
    }
}

package com.example.quiz.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuestionRequest(
        @NotBlank String question,
        List<AnswerRequest> answers
) {
}

package com.example.quiz.dto.request.question;

import com.example.quiz.dto.request.AnswerRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateQuestionRequest(
        Long id,
        @NotBlank String question,
        @NotEmpty List<AnswerRequest> answers
) {}


package com.example.quiz.dto.request.quiz;

import com.example.quiz.dto.request.question.UpdateQuestionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateQuizWithQuestionsRequest(
        Long id,
        @NotBlank String name,
        @NotNull List<UpdateQuestionRequest> questions
) {}

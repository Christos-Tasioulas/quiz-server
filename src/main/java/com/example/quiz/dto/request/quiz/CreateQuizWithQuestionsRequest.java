package com.example.quiz.dto.request.quiz;

import com.example.quiz.dto.request.question.CreateQuestionRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateQuizWithQuestionsRequest(
        @NotBlank String name,
        @NotEmpty List<CreateQuestionRequest> questions
) {}


package com.example.quiz.dto.response;

import com.example.quiz.entities.Question;

import java.util.List;

public record QuestionResponse(
        Long id,
        String question,
        List<AnswerResponse> answers
) {
    public QuestionResponse(Question question) {
        this(
                question.getId(),
                question.getQuestion(),
                question.getAnswers()
                        .stream()
                        .map(AnswerResponse::new)
                        .toList()
        );
    }
}


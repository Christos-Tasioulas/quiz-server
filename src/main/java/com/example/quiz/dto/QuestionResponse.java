package com.example.quiz.dto;

import com.example.quiz.entities.Question;
import lombok.Data;

@Data
public class QuestionResponse {
    private Long id;
    private String question;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
    }
}

package com.example.quiz.dto.response;

import com.example.quiz.entities.Answer;
import lombok.Data;

import java.util.Map;

@Data
public class AnswerResponse {
    private Long id;
    private String answer;
    Map<String, Object> score;

    public AnswerResponse(Answer answer) {
        this.id = answer.getId();
        this.answer = answer.getAnswer();
        this.score = answer.getScore();
    }
}

package com.example.quiz.dto.response;

import com.example.quiz.entities.Answer;
import com.example.quiz.entities.Question;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionResponse {
    private Long id;
    private String question;
    private List<String> answers;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
        this.answers = question.getAnswers().stream()
                .map(Answer::getAnswer)
                .collect(Collectors.toList());
    }
}

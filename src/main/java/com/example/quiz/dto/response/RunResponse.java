package com.example.quiz.dto.response;

import com.example.quiz.entities.Run;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class RunResponse {
    private Long id;
    private String username;
    private int totalQuestions;
    private int questionsAnswered;
    private Map<String, Object> score;
    private double progress;
    List<QuestionAnsweredResponse> questions;
    LocalDateTime startedAt;
    LocalDateTime finishedAt;

    public RunResponse(Run run) {
        this.id = run.getId();
        this.username = run.getUser().getUsername();
        this.totalQuestions = run.getTotalQuestions();
        this.questionsAnswered = run.getQuestionsAnswered();
        this.score = run.getScore();
        this.progress = run.getTotalQuestions() == 0 ? 0 :
                (double) run.getQuestionsAnswered() / run.getTotalQuestions() * 100;
        this.questions = run.getQuestions().stream().map(QuestionAnsweredResponse::new).toList();
        this.startedAt = run.getStartedAt();
        this.finishedAt = run.getFinishedAt();
    }
}

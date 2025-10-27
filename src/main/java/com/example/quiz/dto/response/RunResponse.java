package com.example.quiz.dto.response;

import com.example.quiz.entities.Run;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RunResponse {
    private Long id;
    private String username;
    private int totalQuestions;
    private int questionsAnswered;
    private Map<String, Object> score;
    private double progress;
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
        this.startedAt = run.getStartedAt();
        this.finishedAt = run.getFinishedAt();
    }
}

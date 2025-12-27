package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@Table(
        name = "questions_answered",
        uniqueConstraints = @UniqueConstraint(columnNames = {"run_id", "question_id"})
)
public class QuestionAnswered {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id", nullable = false)
    private Run run;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answer;

    private boolean questionAnswered;

    public QuestionAnswered() {
    }

    public QuestionAnswered(Run run, Question question) {
        this.run = run;
        this.question = question;
        this.questionAnswered = false;
        this.answer = null; // initially unanswered
    }

    public QuestionAnswered(Run run, Question question, Answer answer, boolean questionAnswered) {
        this.run = run;
        this.question = question;
        this.answer = answer;
        this.questionAnswered = questionAnswered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionAnswered)) return false;
        return id != null && id.equals(((QuestionAnswered) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

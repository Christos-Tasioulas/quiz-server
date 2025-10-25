package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity @Data
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    private String answer;

    // Many answers belong to one question
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)  // creates the foreign key
    private Question question;

    public Answer() {
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public Answer(Long id, String answer, Question question) {
        this.id = id;
        this.answer = answer;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer answer1)) return false;
        return Objects.equals(id, answer1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                '}';
    }
}

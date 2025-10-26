package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;
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

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> score;

    public Answer() {
    }

    public Answer(String answer) {
        this.answer = answer;
        this.score = new HashMap<>();
        this.score.put("score", null);
    }

    public Answer(String answer, Map<String, Object> score) {
        this.answer = answer;
        this.score = score != null ? score : new HashMap<>();
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

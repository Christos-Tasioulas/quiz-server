package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity @Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String question;

    public Question() {
    }

    public Question(Long id, String question) {
        this.id = id;
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question question1)) return false;
        return Objects.equals(id, question1.id) && Objects.equals(question, question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question);
    }

    @Override
    public String toString() {
        return "Question: {" +
                "id=" + id +
                ", question='" + question + '\'' +
                '}';
    }
}

package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity @Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String question;

    // One question has many answers
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

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

        String questionString = "Question: {" +
                "id=" + id +
                ", question='" + question + '\'' +
                '}';

        StringBuilder answerBuilder = new StringBuilder();
        int i = 0;
        for(Answer answer : answers) {
            i++;
            answerBuilder.append(i).append(". ").append(answer.toString());
        }

        return questionString + answerBuilder;
    }

    // --- Relationship helpers ---
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);  // Keep both sides in sync
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }
}

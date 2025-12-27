package com.example.quiz.entities;

import com.example.quiz.dto.request.QuestionRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String question;

    // Many questions belong to one quiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)  // creates the foreign key
    private Quiz quiz;

    // One question has many answers
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(Long id, String question) {
        this.id = id;
        this.question = question;
    }

    public Question(QuestionRequest questionRequest) {
        this.question = questionRequest.question();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question question1)) return false;
        return Objects.equals(id, question1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

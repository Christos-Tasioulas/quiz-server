package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Run> runs = new ArrayList<>();

    private int numberOfQuestions;

    protected Quiz() {
        // for JPA
    }

    public Quiz(String name, List<Question> questions) {
        this.name = name;
        if (questions != null) {
            this.numberOfQuestions = questions.size();
            if (this.numberOfQuestions != 0) {
                this.questions = questions;
            }
        }
    }

    // ---------------- equals & hashCode ----------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        // Handles Hibernate proxies correctly
        if (getClass() != o.getClass()) return false;

        Quiz quiz = (Quiz) o;

        // Only compare IDs
        return id != null && id.equals(quiz.id);
    }

    @Override
    public int hashCode() {
        // Constant hash before persistence, stable after
        return getClass().hashCode();
    }

    // ---------------- toString ----------------

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", numberOfQuestions=" + numberOfQuestions +
                '}';
    }

    // ---------------- helpers ----------------

    public void addQuestion(Question question) {
        questions.add(question);
        setNumberOfQuestions(questions.size());
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        setNumberOfQuestions(questions.size());
    }

    public void addRun(Run run) {
        runs.add(run);
    }

    public void removeRun(Run run) {
        runs.remove(run);
    }
}

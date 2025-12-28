package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quizzes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Question> questions = new ArrayList<>();

    @OneToMany(
            mappedBy = "quiz",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Run> runs = new ArrayList<>();

    public Quiz(String name) {
        this.name = Objects.requireNonNull(name, "Quiz name must not be null");
    }

    /* ---------------- Aggregate helpers ---------------- */

    public void rename(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setQuiz(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setQuiz(null);
    }

    public void addRun(Run run) {
        runs.add(run);
        run.setQuiz(this);
    }

    public void removeRun(Run run) {
        runs.remove(run);
        run.setQuiz(null);
    }

    /* ---------------- Derived fields ---------------- */

    @Transient
    public int getNumberOfQuestions() {
        return questions.size();
    }

    /* ---------------- Equality ---------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return id != null && id.equals(quiz.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

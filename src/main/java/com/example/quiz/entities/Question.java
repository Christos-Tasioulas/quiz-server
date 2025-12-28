package com.example.quiz.entities;

import com.example.quiz.dto.request.question.CreateQuestionRequest;
import com.example.quiz.dto.request.question.UpdateQuestionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    /* ---------------- Relations ---------------- */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Answer> answers = new ArrayList<>();

    /* ---------------- Constructors ---------------- */

    public Question(CreateQuestionRequest request) {
        this.question = Objects.requireNonNull(request.question());
        request.answers().forEach(a -> addAnswer(new Answer(a)));
    }

    /* ---------------- Aggregate helpers ---------------- */

    void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }

    public void update(UpdateQuestionRequest request) {
        this.question = request.question();
        answers.clear();
        request.answers().forEach(a -> addAnswer(new Answer(a)));
    }

    /* ---------------- Equality ---------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question other = (Question) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /* ---------------- toString ---------------- */

    @Override
    public String toString() {
        return "Question{id=" + id + ", question='" + question + "'}";
    }
}

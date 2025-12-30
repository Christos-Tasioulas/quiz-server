package com.example.quiz.entities;

import com.example.quiz.dto.request.AnswerRequest;
import com.example.quiz.dto.request.question.CreateQuestionRequest;
import com.example.quiz.dto.request.question.UpdateQuestionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(
        name = "questions",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_question_in_a_quiz", columnNames = {"quiz_id", "question"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    /* ---------------- Relations ---------------- */

    @Setter
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

    public Question(UpdateQuestionRequest request) {
        this.question = Objects.requireNonNull(request.question());
        request.answers().forEach(a -> addAnswer(new Answer(a)));
    }

    /* ---------------- Aggregate helpers ---------------- */

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

        // Map existing answers by ID for lookup
        Map<Long, Answer> existingAnswers = answers.stream()
                .filter(a -> a.getId() != null)
                .collect(Collectors.toMap(Answer::getId, a -> a));

        List<Answer> updatedAnswers = new ArrayList<>();

        for (AnswerRequest aReq : request.answers()) {
            if (aReq.id() != null && existingAnswers.containsKey(aReq.id())) {
                // Update existing answer
                Answer existing = existingAnswers.get(aReq.id());
                existing.update(aReq); // implement update in Answer
                updatedAnswers.add(existing);
            } else {
                // Add new answer
                Answer newAnswer = new Answer(aReq);
                newAnswer.setQuestion(this);
                updatedAnswers.add(newAnswer);
            }
        }

        // Remove deleted answers
        answers.removeIf(a -> !updatedAnswers.contains(a));
        answers.addAll(
                updatedAnswers.stream().filter(a -> !answers.contains(a)).toList()
        );
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

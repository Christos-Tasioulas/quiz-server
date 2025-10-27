package com.example.quiz.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "runs")
public class Run {
    @Id
    @GeneratedValue
    private Long id;

    // Many runs belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // creates the foreign key
    private User user;

    // One run has many questions
    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionAnswered> questions = new ArrayList<>();

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> score;

    private int totalQuestions;

    private int questionsAnswered;

    @CreationTimestamp
    LocalDateTime startedAt;

    @UpdateTimestamp
    LocalDateTime finishedAt;

    public Run() {}

    public Run(Map<String, Object> score, int totalQuestions) {
        this.score = score != null ? score : new HashMap<>();
        this.totalQuestions = totalQuestions;
        this.questionsAnswered = 0;
    }

    public void addQuestionAnswered(QuestionAnswered questionAnswered) {
        questions.add(questionAnswered);
    }

    public void removeQuestionAnswered(QuestionAnswered questionAnswered) {
        questions.remove(questionAnswered);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Run run1)) return false;
        return Objects.equals(id, run1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", score=" + score +
                '}';
    }
}

package com.example.quiz.entities;

import com.example.quiz.dto.request.AnswerRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(
        name = "answers",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_question_answer", columnNames = {"question_id", "answer"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answer;

    /* ---------------- Relations ---------------- */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id")
    private Question question;

    /* ---------------- JSON score ---------------- */

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> score = new HashMap<>();

    /* ---------------- Constructors ---------------- */

    public Answer(AnswerRequest request) {
        this.answer = Objects.requireNonNull(request.answer());
        if (request.score() != null) {
            this.score.putAll(request.score());
        }
    }

    /* ---------------- Package-private helpers ---------------- */

    void setQuestion(Question question) {
        this.question = question;
    }

    /* ---------------- Equality ---------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer other = (Answer) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /* ---------------- toString ---------------- */

    @Override
    public String toString() {
        return "Answer{id=" + id + ", answer='" + answer + "'}";
    }
}

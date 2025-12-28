package com.example.quiz.web;

import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.service.QuestionQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Transactional
public class QuestionController {

    private final QuestionQueryService questionQueryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable Long id) {
        QuestionResponse question = questionQueryService.getById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/by-quiz/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<QuestionResponse> questions = questionQueryService.getByQuiz(quizId);
        if (questions.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204 if no questions found
        }
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/random/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionResponse> getRandomQuestion(@PathVariable Long quizId) {
        QuestionResponse question = questionQueryService.getRandom(quizId);
        return ResponseEntity.ok(question);
    }
}


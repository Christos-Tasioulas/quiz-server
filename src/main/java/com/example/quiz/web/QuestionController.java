package com.example.quiz.web;

import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.service.QuestionQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionQueryService.getById(id);
    }

    @GetMapping("/by-quiz/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<QuestionResponse> getQuestionsByQuiz(
            @PathVariable Long quizId
    ) {
        return questionQueryService.getByQuiz(quizId);
    }

    @GetMapping("/random/{quizId}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionResponse getRandomQuestion(
            @PathVariable Long quizId
    ) {
        return questionQueryService.getRandom(quizId);
    }
}

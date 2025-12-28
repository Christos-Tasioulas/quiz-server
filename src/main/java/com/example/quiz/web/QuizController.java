package com.example.quiz.web;

import com.example.quiz.dto.request.QuizRequest;
import com.example.quiz.dto.response.QuizResponse;
import com.example.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createQuiz(@RequestBody QuizRequest quizRequest) {
        try {
            QuizResponse quizResponse = quizService.createQuiz(quizRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(quizResponse.id())
                    .toUri();
            return ResponseEntity.created(location).body(quizResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getQuizById/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable Long id) {
        QuizResponse quizResponse = quizService.getQuizById(id);
        return ResponseEntity.ok(quizResponse);
    }

    @GetMapping("/getQuizByName/{name}")
    public ResponseEntity<?> getQuizById(@PathVariable String name) {
        QuizResponse quizResponse = quizService.getQuizByName(name);
        return ResponseEntity.ok(quizResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateQuiz(@RequestBody QuizRequest quizRequest, @PathVariable Long id) {
        QuizResponse quizResponse = quizService.editQuiz(quizRequest, id);
        return ResponseEntity.ok(quizResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

}

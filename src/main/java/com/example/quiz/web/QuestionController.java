package com.example.quiz.web;

import com.example.quiz.dto.request.QuestionRequest;
import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.entities.Answer;
import com.example.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createQuestion(@RequestBody QuestionRequest request) {
        try {
            QuestionResponse questionResponse = questionService.createQuestion(request);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(questionResponse.getId())
                    .toUri();
            return ResponseEntity.created(location).body(questionResponse);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')") // Requires ADMIN role
    public List<QuestionResponse> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/questionById/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long id) {
        QuestionResponse question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/randomQuestion")
    public ResponseEntity<?> getRandomQuestion() {
        QuestionResponse question = questionService.getRandomQuestion();
        return ResponseEntity.ok(question);
    }

    @GetMapping("/{id}/answers")
    public ResponseEntity<?> getAnswersByQuestion(@PathVariable Long id) {
        List<Answer> answers = questionService.getAnswersByQuestion(id);
        return ResponseEntity.ok(answers);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editQuestion(@RequestBody QuestionRequest newQuestion, @PathVariable Long id) {
        QuestionResponse questionResponse = questionService.editQuestion(newQuestion, id);
        return ResponseEntity.ok(questionResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.ok("Question deleted successfully");
    }
}

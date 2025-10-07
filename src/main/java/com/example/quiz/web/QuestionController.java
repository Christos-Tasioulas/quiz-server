package com.example.quiz.web;

import com.example.quiz.dto.QuestionResponse;
import com.example.quiz.entities.Question;
import com.example.quiz.service.QuestionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> createQuestion(@RequestBody Question question, HttpServletResponse response) {
        try {
            QuestionResponse questionResponse = questionService.createQuestion(question);
            return ResponseEntity.ok().body(questionResponse);
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editQuestion(@RequestBody Map<String, Object> newQuestion, @PathVariable Long id) {
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

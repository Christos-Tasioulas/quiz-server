package com.example.quiz.web;

import com.example.quiz.dto.request.QuestionAnsweredRequest;
import com.example.quiz.dto.response.QuestionAnsweredResponse;
import com.example.quiz.service.QuestionAnsweredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questionsAnswered")
public class QuestionAnsweredController {

    @Autowired
    private final QuestionAnsweredService questionAnsweredService;

    public QuestionAnsweredController(QuestionAnsweredService questionAnsweredService) {
        this.questionAnsweredService = questionAnsweredService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionAnsweredById(@PathVariable Long id) {
        QuestionAnsweredResponse questionAnsweredResponse = questionAnsweredService.getQuestionAnsweredById(id);
        return ResponseEntity.ok(questionAnsweredResponse);
    }

    @GetMapping("/questionsAnsweredByRun/{id}")
    public List<QuestionAnsweredResponse> getQuestionsAnsweredByRun(@PathVariable Long id) {
        return questionAnsweredService.getQuestionsAnsweredByRun(id);
    }

    @PutMapping("/answer/{id}")
    public ResponseEntity<?> answerQuestion(@PathVariable Long id, @RequestBody QuestionAnsweredRequest questionAnsweredRequest) {
        QuestionAnsweredResponse questionAnsweredResponse = questionAnsweredService.answerQuestion(id, questionAnsweredRequest);
        return ResponseEntity.ok(questionAnsweredResponse);
    }

    @PutMapping("/removeAnswer/{id}")
    public ResponseEntity<?> removeAnswer(@PathVariable Long id, @RequestBody QuestionAnsweredRequest questionAnsweredRequest) {
        QuestionAnsweredResponse questionAnsweredResponse = questionAnsweredService.removeAnswer(id, questionAnsweredRequest);
        return ResponseEntity.ok(questionAnsweredResponse);
    }
}

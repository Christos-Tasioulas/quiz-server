package com.example.quiz.web;

import com.example.quiz.dto.request.question.CreateQuestionRequest;
import com.example.quiz.dto.request.question.UpdateQuestionRequest;
import com.example.quiz.dto.request.quiz.CreateQuizRequest;
import com.example.quiz.dto.request.quiz.CreateQuizWithQuestionsRequest;
import com.example.quiz.dto.request.quiz.UpdateQuizWithQuestionsRequest;
import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.dto.response.QuizResponse;
import com.example.quiz.entities.Quiz;
import com.example.quiz.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    /* ---------------- Quizzes ---------------- */

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizResponse> createQuiz(
            @Valid @RequestBody CreateQuizRequest request
    ) {
        Quiz quiz = quizService.createQuiz(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(quiz.getId())
                .toUri();

        return ResponseEntity.created(location).body(new QuizResponse(quiz));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizResponse> createQuizWithQuestions(
            @Valid @RequestBody CreateQuizWithQuestionsRequest request
    ) {
        Quiz quiz = quizService.createQuizWithQuestions(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(quiz.getId())
                .toUri();

        return ResponseEntity.created(location).body(new QuizResponse(quiz));
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
        List<QuizResponse> quizzes = quizService.getAllQuizzes();
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> getQuizById(@PathVariable Long id) {
        QuizResponse quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<QuizResponse> getQuizByName(@PathVariable String name) {
        QuizResponse quiz = quizService.getQuizByName(name);
        return ResponseEntity.ok(quiz);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizResponse> updateQuiz(
            @PathVariable Long id,
            @Valid @RequestBody CreateQuizRequest request
    ) {
        QuizResponse updatedQuiz = quizService.updateQuiz(id, request);
        return ResponseEntity.ok(updatedQuiz);
    }

    @PutMapping("/bulk/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuizResponse> updateQuizWithQuestions(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuizWithQuestionsRequest request
    ) {
        QuizResponse quizResponse = quizService.editQuizWithQuestions(id, request);
        return ResponseEntity.ok(quizResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    /* ---------------- Questions ---------------- */

    @PostMapping("/{quizId}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionResponse> addQuestion(
            @PathVariable Long quizId,
            @Valid @RequestBody CreateQuestionRequest request
    ) {
        QuestionResponse question = quizService.addQuestion(quizId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @PutMapping("/{quizId}/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @Valid @RequestBody UpdateQuestionRequest request
    ) {
        QuestionResponse question = quizService.updateQuestion(quizId, questionId, request);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/{quizId}/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long quizId,
            @PathVariable Long questionId
    ) {
        quizService.deleteQuestion(quizId, questionId);
        return ResponseEntity.noContent().build();
    }
}

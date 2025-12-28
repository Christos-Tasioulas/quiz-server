package com.example.quiz.web;

import com.example.quiz.dto.request.question.CreateQuestionRequest;
import com.example.quiz.dto.request.question.UpdateQuestionRequest;
import com.example.quiz.dto.request.quiz.CreateQuizRequest;
import com.example.quiz.dto.request.quiz.CreateQuizWithQuestionsRequest;
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
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new QuizResponse(quizService.createQuizWithQuestions(request)));
    }

    @GetMapping
    public List<QuizResponse> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public QuizResponse getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/by-name/{name}")
    public QuizResponse getQuizByName(@PathVariable String name) {
        return quizService.getQuizByName(name);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuizResponse updateQuiz(
            @PathVariable Long id,
            @Valid @RequestBody CreateQuizRequest request
    ) {
        return quizService.updateQuiz(id, request);
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
    public QuestionResponse addQuestion(
            @PathVariable Long quizId,
            @Valid @RequestBody CreateQuestionRequest request
    ) {
        return quizService.addQuestion(quizId, request);
    }

    @PutMapping("/{quizId}/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionResponse updateQuestion(
            @PathVariable Long quizId,
            @PathVariable Long questionId,
            @Valid @RequestBody UpdateQuestionRequest request
    ) {
        return quizService.updateQuestion(quizId, questionId, request);
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

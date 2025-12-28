package com.example.quiz.service;

import com.example.quiz.dto.request.AnswerRequest;
import com.example.quiz.dto.request.question.CreateQuestionRequest;
import com.example.quiz.dto.request.question.UpdateQuestionRequest;
import com.example.quiz.dto.request.quiz.CreateQuizRequest;
import com.example.quiz.dto.request.quiz.CreateQuizWithQuestionsRequest;
import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.dto.response.QuizResponse;
import com.example.quiz.entities.Question;
import com.example.quiz.entities.Quiz;
import com.example.quiz.exceptions.badRequest.DuplicateAnswersException;
import com.example.quiz.exceptions.badRequest.InsufficientAnswersException;
import com.example.quiz.exceptions.notFound.QuestionNotFoundException;
import com.example.quiz.exceptions.notFound.QuizNotFoundException;
import com.example.quiz.repositories.QuestionRepository;
import com.example.quiz.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    /* -------------------------------------------------
     * Quiz lifecycle
     * ------------------------------------------------- */

    public Quiz createQuiz(CreateQuizRequest request) {

        if (quizRepository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Quiz with name '" + request.name() + "' already exists"
            );
        }

        Quiz quiz = new Quiz(request.name());
        return quizRepository.save(quiz);
    }

    public Quiz createQuizWithQuestions(CreateQuizWithQuestionsRequest request) {

        if (quizRepository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Quiz with name '" + request.name() + "' already exists"
            );
        }

        Quiz quiz = new Quiz(request.name());

        request.questions().forEach(qr -> {
            Question question = new Question(qr);
            quiz.addQuestion(question); // bidirectional, safe
        });

        return quizRepository.save(quiz);
    }

    public List<QuizResponse> getAllQuizzes() {
        return quizRepository.findAll()
                .stream()
                .map(QuizResponse::new)
                .toList();
    }

    public QuizResponse getQuizById(Long id) {
        return quizRepository.findById(id)
                .map(QuizResponse::new)
                .orElseThrow(() -> new QuizNotFoundException(id));
    }

    public QuizResponse getQuizByName(String name) {
        return quizRepository.findByName(name)
                .map(QuizResponse::new)
                .orElseThrow(() ->
                        new QuizNotFoundException("Quiz not found with name: " + name)
                );
    }

    public QuizResponse updateQuiz(Long id, CreateQuizRequest request) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        if (!quiz.getName().equals(request.name())
                && quizRepository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Quiz with name '" + request.name() + "' already exists"
            );
        }

        quiz.rename(request.name());
        return new QuizResponse(quiz);
    }

    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        quizRepository.delete(quiz);
    }

    /* -------------------------------------------------
     * Question lifecycle
     * ------------------------------------------------- */

    public QuestionResponse addQuestion(Long quizId, CreateQuestionRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (request.answers() == null || request.answers().size() < 2) {
            throw new InsufficientAnswersException(request.question());
        }

        // Optional: check duplicate answers
        Set<String> distinctAnswers = request.answers().stream()
                .map(AnswerRequest::answer)
                .collect(Collectors.toSet());
        if (distinctAnswers.size() != request.answers().size()) {
            throw new DuplicateAnswersException(request.question());
        }

        Question question = new Question(request);
        quiz.addQuestion(question);

        quizRepository.saveAndFlush(quiz); // persist cascade

        return new QuestionResponse(question);
    }

    public QuestionResponse updateQuestion(
            Long quizId,
            Long questionId,
            UpdateQuestionRequest request
    ) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        Question question = quiz.getQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        // --- Validate answers ---
        List<AnswerRequest> answers = request.answers();
        if (answers == null || answers.size() < 2) {
            throw new InsufficientAnswersException(request.question());
        }

        // Optional: check for duplicate answers
        Set<String> distinctAnswers = answers.stream()
                .map(AnswerRequest::answer)
                .collect(Collectors.toSet());
        if (distinctAnswers.size() != answers.size()) {
            throw new DuplicateAnswersException(request.question());
        }

        // --- Update the question and answers ---
        question.update(request);

        // --- Persist via aggregate root ---
        quizRepository.saveAndFlush(quiz);

        return new QuestionResponse(question);
    }


    public void deleteQuestion(Long quizId, Long questionId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        Question question = quiz.getQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() ->
                        new QuestionNotFoundException(questionId)
                );

        quiz.removeQuestion(question);
        questionRepository.delete(question);
        quizRepository.saveAndFlush(quiz);
    }
}

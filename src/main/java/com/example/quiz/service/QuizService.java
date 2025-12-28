package com.example.quiz.service;

import com.example.quiz.dto.request.QuestionRequest;
import com.example.quiz.dto.request.QuizRequest;
import com.example.quiz.dto.response.QuizResponse;
import com.example.quiz.entities.Question;
import com.example.quiz.entities.Quiz;
import com.example.quiz.exceptions.QuizNotFoundException;
import com.example.quiz.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private final QuizRepository quizRepository;

    @Transactional
    public QuizResponse createQuiz(QuizRequest quizRequest) {

        if (quizRepository.existsByName(quizRequest.name())) {
            throw new IllegalArgumentException("Quiz " + quizRequest.name() + " already exists");
        }

        Quiz quiz = new Quiz(quizRequest.name(), null);

        if (quizRequest.questions() != null) {
            for (QuestionRequest qr : quizRequest.questions()) {
                Question question = new Question(qr);
                quiz.addQuestion(question);     // updates count
                question.setQuiz(quiz);         // sets FK
            }
        }

        quizRepository.save(quiz);
        return new QuizResponse(quiz);
    }

    public List<QuizResponse> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(QuizResponse::new)
                .collect(Collectors.toList());
    }

    public QuizResponse getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        return new QuizResponse(quiz);
    }

    public QuizResponse getQuizByName(String name) {
        Quiz quiz = quizRepository.findByName(name)
                .orElseThrow(() ->
                        new QuizNotFoundException("Could not find quiz with name: " + name)
                );
        return new QuizResponse(quiz);
    }

    @Transactional
    public QuizResponse editQuiz(QuizRequest quizRequest, Long id) {

        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found with id " + id));

        if (!quiz.getName().equals(quizRequest.name())
                && quizRepository.existsByName(quizRequest.name())) {
            throw new IllegalArgumentException("Quiz " + quizRequest.name() + " already exists");
        }

        quiz.setName(quizRequest.name());

        Map<Long, Question> existingQuestions = quiz.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        List<Question> updatedQuestions = new ArrayList<>();

        for (QuestionRequest qr : quizRequest.questions()) {
            if (qr.id() != null && existingQuestions.containsKey(qr.id())) {
                // update existing
                Question q = existingQuestions.get(qr.id());
                q.setQuestion(qr.question());
                updatedQuestions.add(q);
            } else {
                // new question
                Question q = new Question(qr);
                q.setQuiz(quiz);
                updatedQuestions.add(q);
            }
        }

        quiz.getQuestions().clear();
        quiz.getQuestions().addAll(updatedQuestions);
        quiz.setNumberOfQuestions(updatedQuestions.size());

        quizRepository.saveAndFlush(quiz);

        return new QuizResponse(quiz);
    }


    @Transactional
    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        quizRepository.delete(quiz);
    }
}

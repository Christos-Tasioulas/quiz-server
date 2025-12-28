package com.example.quiz.service;

import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.entities.Question;
import com.example.quiz.exceptions.notFound.QuestionNotFoundException;
import com.example.quiz.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionQueryService {

    private final QuestionRepository questionRepository;

    public QuestionResponse getById(Long id) {
        return questionRepository.findById(id)
                .map(QuestionResponse::new)
                .orElseThrow(() -> new QuestionNotFoundException(id));
    }

    public List<QuestionResponse> getByQuiz(Long quizId) {
        return questionRepository.findQuestionsByQuizId(quizId)
                .stream()
                .map(QuestionResponse::new)
                .toList();
    }

    public QuestionResponse getRandom(Long quizId) {
        Question q = questionRepository.findRandomQuestionByQuizId(quizId);
        return new QuestionResponse(q);
    }
}


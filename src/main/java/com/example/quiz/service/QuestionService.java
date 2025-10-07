package com.example.quiz.service;

import com.example.quiz.dto.QuestionResponse;
import com.example.quiz.entities.Question;
import com.example.quiz.exceptions.QuestionNotFoundException;
import com.example.quiz.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionResponse createQuestion(Question question) {
        if (questionRepository.existsByQuestion(question.getQuestion())) {
            throw new IllegalArgumentException("Question " + question.getQuestion() + " already exists");
        }

        questionRepository.save(question);

        return new QuestionResponse(question);
    }

    public List<QuestionResponse> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(QuestionResponse::new)
                .collect(Collectors.toList());
    }

    public QuestionResponse getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return new QuestionResponse(question);
    }

    public QuestionResponse getRandomQuestion() {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions found");
        }

        Random random = new Random();
        Question question = questions.get(random.nextInt(questions.size()));
        return new QuestionResponse(question);
    }

    public QuestionResponse editQuestion(Map<String, Object> newQuestion, Long id) {
        Question question = questionRepository.findById(id)
                .map(existingQuestion -> {
                    if (newQuestion.containsKey("question")) existingQuestion.setQuestion((String) newQuestion.get("question"));
                    return questionRepository.save(existingQuestion);
                }).orElseThrow(() -> new QuestionNotFoundException(id));

        return new QuestionResponse(question);
    }

    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        questionRepository.deleteById(id);
    }
}

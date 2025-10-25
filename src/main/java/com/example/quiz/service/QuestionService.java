package com.example.quiz.service;

import com.example.quiz.dto.request.QuestionRequest;
import com.example.quiz.dto.response.QuestionResponse;
import com.example.quiz.entities.Answer;
import com.example.quiz.entities.Question;
import com.example.quiz.exceptions.QuestionNotFoundException;
import com.example.quiz.repositories.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionResponse createQuestion(QuestionRequest request) {

        Question question = new Question();
        question.setQuestion(request.question());

        if (questionRepository.existsByQuestion(question.getQuestion())) {
            throw new IllegalArgumentException("Question " + question.getQuestion() + " already exists");
        }

        if (request.answers() != null) {
            request.answers().forEach(aReq -> {
                Answer answer = new Answer(aReq.answer());
                question.addAnswer(answer); // sets both sides of relationship
            });
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
        Question question = questionRepository.findRandomQuestion();
        return new QuestionResponse(question);
    }

    public List<Answer> getAnswersByQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return question.getAnswers();
    }

    @Transactional
    public QuestionResponse editQuestion(QuestionRequest request, Long id) {
        // Fetch the question
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        // --- Update question text if provided ---
        if (request.question() != null && !request.question().isBlank()) {
            question.setQuestion(request.question());
        }

        // --- Update answers if provided ---
        if (request.answers() != null) {
            // 1️⃣ Remove answers that are not in the new list
            List<Answer> toRemove = question.getAnswers().stream()
                    .filter(existing -> request.answers().stream()
                            .noneMatch(aReq -> aReq.answer().equals(existing.getAnswer())))
                    .toList();

            toRemove.forEach(question::removeAnswer);

            // 2️⃣ Add new answers (avoid duplicates)
            request.answers().forEach(aReq -> {
                boolean exists = question.getAnswers().stream()
                        .anyMatch(existing -> existing.getAnswer().equals(aReq.answer()));
                if (!exists) {
                    Answer newAnswer = new Answer(aReq.answer());
                    question.addAnswer(newAnswer);
                }
            });
        }

        // --- Save question (cascade handles answers) ---
        Question updated = questionRepository.save(question);

        // Return DTO
        return new QuestionResponse(updated);
    }




    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException(id);
        }
        questionRepository.deleteById(id);
    }
}

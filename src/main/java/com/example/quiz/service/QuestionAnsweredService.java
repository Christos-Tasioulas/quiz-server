package com.example.quiz.service;

import com.example.quiz.dto.request.QuestionAnsweredRequest;
import com.example.quiz.dto.response.QuestionAnsweredResponse;
import com.example.quiz.entities.Answer;
import com.example.quiz.entities.QuestionAnswered;
import com.example.quiz.exceptions.notFound.AnswerNotFoundException;
import com.example.quiz.exceptions.notFound.QuestionNotFoundException;
import com.example.quiz.repositories.AnswerRepository;
import com.example.quiz.repositories.QuestionAnsweredRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionAnsweredService {

    private final QuestionAnsweredRepository questionAnsweredRepository;
    private final AnswerRepository answerRepository;

    public QuestionAnsweredResponse getQuestionAnsweredById(Long id) {
        QuestionAnswered questionAnswered = questionAnsweredRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        return new QuestionAnsweredResponse(questionAnswered);
    }

    public List<QuestionAnsweredResponse> getQuestionsAnsweredByRun(Long id) {
        List<QuestionAnswered> questionAnswered = questionAnsweredRepository.findByRunId(id);
        return questionAnswered.stream().map(QuestionAnsweredResponse::new).toList();
    }

    public QuestionAnsweredResponse answerQuestion(Long id, QuestionAnsweredRequest questionAnsweredRequest) {
        QuestionAnswered questionAnswered = questionAnsweredRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        questionAnswered.setQuestionAnswered(true);
        Answer chosenAnswer = answerRepository.findById(questionAnsweredRequest.answerId()).orElseThrow(() -> new AnswerNotFoundException(questionAnsweredRequest.answerId()));
        questionAnswered.setAnswer(chosenAnswer);
        questionAnsweredRepository.save(questionAnswered);
        return new QuestionAnsweredResponse(questionAnswered);
    }

    public QuestionAnsweredResponse removeAnswer(Long id, QuestionAnsweredRequest questionAnsweredRequest) {
        QuestionAnswered questionAnswered = questionAnsweredRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
        questionAnswered.setQuestionAnswered(false);
        questionAnswered.setAnswer(null);
        questionAnsweredRepository.save(questionAnswered);
        return new QuestionAnsweredResponse(questionAnswered);
    }
}

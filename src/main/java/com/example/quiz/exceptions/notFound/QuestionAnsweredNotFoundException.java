package com.example.quiz.exceptions.notFound;

public class QuestionAnsweredNotFoundException extends RuntimeException {
    public QuestionAnsweredNotFoundException(String message) {
        super(message);
    }

    public QuestionAnsweredNotFoundException(Long id) {
        super("QuestionAnswered could not be found with id: " + id);
    }
}

package com.example.quiz.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(Long id) {super("Question could not be found with id: " + id);}
}

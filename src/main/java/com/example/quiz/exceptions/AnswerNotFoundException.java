package com.example.quiz.exceptions;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(Long id) {
        super("Answer could not be found with id: " + id);
    }
}

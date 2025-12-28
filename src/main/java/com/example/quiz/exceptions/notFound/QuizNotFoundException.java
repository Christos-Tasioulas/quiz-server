package com.example.quiz.exceptions.notFound;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(Long id) {
        super("Could not find quiz with id " + id);
    }
}

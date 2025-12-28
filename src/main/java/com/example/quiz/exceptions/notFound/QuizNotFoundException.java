package com.example.quiz.exceptions.notFound;

import jakarta.persistence.EntityNotFoundException;

public class QuizNotFoundException extends EntityNotFoundException {
    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(Long id) {
        super("Could not find quiz with id " + id);
    }
}

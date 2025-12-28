package com.example.quiz.exceptions.notFound;

import jakarta.persistence.EntityNotFoundException;

public class QuestionNotFoundException extends EntityNotFoundException {
    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(Long id) {super("Question could not be found with id: " + id);}
}

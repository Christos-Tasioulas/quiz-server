package com.example.quiz.exceptions.notFound;

import jakarta.persistence.EntityNotFoundException;

public class QuestionAnsweredNotFoundException extends EntityNotFoundException {
    public QuestionAnsweredNotFoundException(String message) {
        super(message);
    }

    public QuestionAnsweredNotFoundException(Long id) {
        super("QuestionAnswered could not be found with id: " + id);
    }
}

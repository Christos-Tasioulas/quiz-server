package com.example.quiz.exceptions.notFound;

import jakarta.persistence.EntityNotFoundException;

public class RunNotFoundException extends EntityNotFoundException {
    public RunNotFoundException(String message) {
        super(message);
    }

    public RunNotFoundException(Long id) {
        super("Could not find run with id: " + id);
    }
}

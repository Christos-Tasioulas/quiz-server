package com.example.quiz.exceptions;

public class RunNotFoundException extends RuntimeException {
    public RunNotFoundException(String message) {
        super(message);
    }

    public RunNotFoundException(Long id) {
        super("Could not find run with id: " + id);
    }
}

package com.example.quiz.exceptions.notFound;

public class RunNotFoundException extends RuntimeException {
    public RunNotFoundException(String message) {
        super(message);
    }

    public RunNotFoundException(Long id) {
        super("Could not find run with id: " + id);
    }
}

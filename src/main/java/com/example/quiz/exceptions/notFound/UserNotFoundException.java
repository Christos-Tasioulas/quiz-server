package com.example.quiz.exceptions.notFound;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("Could not find user with id: " + id);
    }
}

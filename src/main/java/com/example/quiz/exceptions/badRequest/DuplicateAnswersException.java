package com.example.quiz.exceptions.badRequest;

public class DuplicateAnswersException extends RuntimeException {
    public DuplicateAnswersException(String question) {
        super("Question: " + question + " has duplicate answers");
    }
}

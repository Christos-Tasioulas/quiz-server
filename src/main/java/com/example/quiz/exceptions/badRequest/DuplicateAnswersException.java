package com.example.quiz.exceptions.badRequest;

public class DuplicateAnswersException extends IllegalArgumentException {
    public DuplicateAnswersException(String question) {
        super("Question: " + question + " has duplicate answers");
    }
}

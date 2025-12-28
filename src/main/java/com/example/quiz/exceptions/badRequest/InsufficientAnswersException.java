package com.example.quiz.exceptions.badRequest;

public class InsufficientAnswersException extends RuntimeException {
    public InsufficientAnswersException(String question) {
        super("Question: " + question + " has less than 2 answers");
    }
}

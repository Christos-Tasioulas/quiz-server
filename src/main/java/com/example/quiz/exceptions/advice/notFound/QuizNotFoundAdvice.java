package com.example.quiz.exceptions.advice.notFound;

import com.example.quiz.exceptions.notFound.QuizNotFoundException;
import com.example.quiz.utils.common.BaseAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class QuizNotFoundAdvice extends BaseAdvice {

    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse quizNotFoundHandler(QuizNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

}

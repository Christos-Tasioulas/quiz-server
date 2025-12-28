package com.example.quiz.exceptions.advice.badRequest;

import com.example.quiz.exceptions.badRequest.InsufficientAnswersException;
import com.example.quiz.utils.common.BaseAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

public class InsufficientAnswersAdvice extends BaseAdvice {
    @ExceptionHandler(InsufficientAnswersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse insufficientAnswersHandler(InsufficientAnswersException ex) {
        return new BaseAdvice.ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}

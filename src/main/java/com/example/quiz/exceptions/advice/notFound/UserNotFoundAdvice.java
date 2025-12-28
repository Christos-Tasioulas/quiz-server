package com.example.quiz.exceptions.advice.notFound;

import com.example.quiz.exceptions.notFound.UserNotFoundException;
import com.example.quiz.utils.common.BaseAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserNotFoundAdvice extends BaseAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse userNotFoundHandler(UserNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}


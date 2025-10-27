package com.example.quiz.exceptions.advice;

import com.example.quiz.exceptions.QuestionAnsweredNotFoundException;
import com.example.quiz.utils.common.BaseAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class QuestionAnsweredNotFoundAdvice extends BaseAdvice {

    @ExceptionHandler(QuestionAnsweredNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse questionAnsweredNotFoundHandler(QuestionAnsweredNotFoundException ex) {
        return new BaseAdvice.ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}

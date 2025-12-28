package com.example.quiz.exceptions.advice.badRequest;

import com.example.quiz.exceptions.badRequest.DuplicateAnswersException;
import com.example.quiz.utils.common.BaseAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

public class DuplicateAnswersAdvice extends BaseAdvice {
    @ExceptionHandler(DuplicateAnswersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse duplicateAnswersHandler(DuplicateAnswersException ex) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}

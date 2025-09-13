package com.example.quiz.utils.common;

import lombok.Data;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BaseAdvice {
    @Data
    protected static class ErrorResponse {
        private int status;
        private String message;
        private LocalDateTime timestamp;

        // constructor
        public ErrorResponse(int status, String message, LocalDateTime timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
        }
    }
}

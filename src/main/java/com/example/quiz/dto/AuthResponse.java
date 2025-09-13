package com.example.quiz.dto;

public record AuthResponse(
        String token,
        UserResponse user
) {}

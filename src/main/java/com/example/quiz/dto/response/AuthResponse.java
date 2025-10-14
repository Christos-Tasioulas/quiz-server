package com.example.quiz.dto.response;

public record AuthResponse(
        String token,
        UserResponse user
) {}

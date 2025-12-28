package com.example.quiz.web;

import com.example.quiz.dto.response.AuthResponse;
import com.example.quiz.dto.request.LoginRequest;
import com.example.quiz.entities.User;
import com.example.quiz.exceptions.UnauthorizedException;
import com.example.quiz.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /* ---------------- Register ---------------- */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(
            @Valid @RequestBody User user,
            HttpServletResponse response
    ) throws UnauthorizedException {
        AuthResponse authResponse = authService.registerUser(user);

        // Add JWT cookie
        Cookie jwtCookie = createCookie(authResponse.token(), 7 * 24 * 60 * 60); // 7 days
        response.addCookie(jwtCookie);

        // Build Location header for the newly created user
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(authResponse.user().getId())
                .toUri();

        return ResponseEntity.created(location).body(authResponse);
    }

    /* ---------------- Login ---------------- */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) throws UnauthorizedException {
        AuthResponse authResponse = authService.authenticateUser(loginRequest);

        // Add JWT cookie
        Cookie jwtCookie = createCookie(authResponse.token(), 7 * 24 * 60 * 60);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(authResponse);
    }

    /* ---------------- Logout ---------------- */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Invalidate JWT cookie
        Cookie jwtCookie = createCookie("", 0);
        response.addCookie(jwtCookie);

        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /* ---------------- Helper to create cookie ---------------- */
    private Cookie createCookie(String token, int expiry) {
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Set to true in production (HTTPS only)
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(expiry);
        jwtCookie.setAttribute("SameSite", "Strict");
        return jwtCookie;
    }
}

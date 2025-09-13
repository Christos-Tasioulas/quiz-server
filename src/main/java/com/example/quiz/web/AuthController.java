package com.example.quiz.web;

import com.example.quiz.dto.AuthResponse;
import com.example.quiz.entities.User;
import com.example.quiz.exceptions.UnauthorizedException;
import com.example.quiz.repositories.LoginRequest;
import com.example.quiz.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private Cookie createCookie(String token, int expiry) {
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Set to true in production (HTTPS only)
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(expiry);
        jwtCookie.setAttribute("SameSite", "Strict");
        return jwtCookie;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.registerUser(user);
            Cookie jwtCookie = createCookie(authResponse.token(), 7 * 24 * 60 * 60);
            response.addCookie(jwtCookie);
            return ResponseEntity.ok().body(authResponse);
        } catch (IllegalArgumentException | UnauthorizedException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            AuthResponse authResponse = authService.authenticateUser(loginRequest);
            Cookie jwtCookie = createCookie(authResponse.token(), 7 * 24 * 60 * 60);
            response.addCookie(jwtCookie);
            return ResponseEntity.ok().body(authResponse);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        Cookie jwtCookie = createCookie("", 0);
        response.addCookie(jwtCookie);
        return ResponseEntity.ok("Logged out successfully");
    }
}

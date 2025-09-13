package com.example.quiz.service;

import com.example.quiz.dto.AuthResponse;
import com.example.quiz.dto.UserResponse;
import com.example.quiz.entities.User;
import com.example.quiz.exceptions.UnauthorizedException;
import com.example.quiz.repositories.LoginRequest;
import com.example.quiz.repositories.UserRepository;
import com.example.quiz.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtUtil jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse registerUser(User user) throws UnauthorizedException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        String hashedPassword = passwordEncoder.encode(new String(user.getPassword()));
        user.setPassword(hashedPassword.toCharArray());

        User savedUser = userRepository.save(user);
        String jwtToken = jwtUtils.generateToken(savedUser.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Failed to authenticate user: " + savedUser.getId()));

        return new AuthResponse(jwtToken, new UserResponse(savedUser));
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) throws UnauthorizedException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    )
            );

            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

            String jwtToken = jwtUtils.generateToken(user.getUsername())
                    .orElseThrow(() -> new AuthenticationServiceException("Token generation failed"));

            return new AuthResponse(jwtToken, new UserResponse(user));

        } catch (BadCredentialsException | DisabledException | LockedException e) {
            throw new UnauthorizedException("Authentication failed: " + e.getMessage());
        }
    }
}

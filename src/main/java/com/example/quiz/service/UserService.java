package com.example.quiz.service;

import com.example.quiz.dto.UserResponse;
import com.example.quiz.entities.Themes;
import com.example.quiz.entities.User;
import com.example.quiz.entities.UserRoles;
import com.example.quiz.exceptions.UserNotFoundException;
import com.example.quiz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Could not find user with username: " + username));
        return new UserResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Could not find user with email: " + email));
        return new UserResponse(user);
    }

    public UserResponse editUser(Map<String, Object> newUser, Long id) {
        User user = userRepository.findById(id)
                .map(existingUser -> {
                    if (newUser.containsKey("firstName")) existingUser.setFirstName((String) newUser.get("firstName"));
                    if (newUser.containsKey("lastName")) existingUser.setLastName((String) newUser.get("lastName"));
                    if (newUser.containsKey("email")) existingUser.setEmail((String) newUser.get("email"));
                    if (newUser.containsKey("password"))
                        existingUser.setPassword(passwordEncoder.encode((String) newUser.get("password")).toCharArray());
                    if (newUser.containsKey("username")) existingUser.setUsername((String) newUser.get("username"));
                    if (newUser.containsKey("role"))
                        existingUser.setRole(UserRoles.valueOf((String) newUser.get("role")));
                    if (newUser.containsKey("preferredTheme"))
                        existingUser.setPreferredTheme(Themes.valueOf((String) newUser.get("preferredTheme")));
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));

        return new UserResponse(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}

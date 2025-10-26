package com.example.quiz.service;

import com.example.quiz.entities.User;
import com.example.quiz.exceptions.UserNotFoundException;
import com.example.quiz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username.trim()).orElseThrow(
                () -> new UserNotFoundException("Could not find user with username: " + username)
        );
        if (user == null) {
            throw new UserNotFoundException("User Not Found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                String.valueOf(user.getPassword()),
                Collections.emptyList()
        );
    }
}

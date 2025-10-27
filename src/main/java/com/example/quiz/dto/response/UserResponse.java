package com.example.quiz.dto.response;

import com.example.quiz.entities.Themes;
import com.example.quiz.entities.User;
import com.example.quiz.entities.UserRoles;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserRoles role;
    private Themes preferredTheme;
    private List<RunResponse> runs = new ArrayList<>();

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.preferredTheme = user.getPreferredTheme();
        this.runs = user.getRuns() != null ? user.getRuns().stream().map(RunResponse::new).toList() : this.runs;
    }
}


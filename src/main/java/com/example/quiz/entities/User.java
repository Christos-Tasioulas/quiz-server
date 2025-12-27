package com.example.quiz.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private char[] password;

    @Enumerated(EnumType.STRING)
    UserRoles role;

    @Enumerated(EnumType.STRING)
    Themes preferredTheme;

    // One user has many runs
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Run> runs = new ArrayList<>();

    User() {}

    public User(Long id, String username, String firstName, String lastName, String email, char[] password, UserRoles role, Themes preferredTheme) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.preferredTheme = preferredTheme;
    }

    public void addRun(Run run) {
        runs.add(run);
        run.setUser(this);  // Keep both sides in sync
    }

    public void removeRun(Run run) {
        runs.remove(run);
        run.setUser(null);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User user))
            return false;
        return Objects.equals(this.id, user.id) && Objects.equals(this.username, user.username)
                && Objects.equals(this.role, user.role) && Arrays.equals(this.password, user.password)
                && Objects.equals(this.email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username, this.email, this.role);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", name='" + this.username + '\'' + ", email='" + this.email + '\'' + ", role='" + this.role + '\'' + '}';
    }
}


package com.smartlibrary.backend.controller;

import com.smartlibrary.backend.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    public UserController() {

        users.add(new User(
                1L,
                "Sathwik",
                "sathwik@gmail.com",
                "1234",
                "STUDENT"
        ));
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        for (User existingUser : users) {

            if (existingUser.getEmail()
                    .equalsIgnoreCase(user.getEmail())) {

                return "Email already registered";
            }
        }

        Long newId = users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0) + 1;

        user.setId(newId);

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("STUDENT");
        }

        users.add(user);

        return "Registration successful";
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginUser) {

        for (User user : users) {

            if (user.getEmail()
                    .equalsIgnoreCase(loginUser.getEmail())
                    && user.getPassword()
                    .equals(loginUser.getPassword())) {

                return "Login successful";
            }
        }

        return "Invalid email or password";
    }
}
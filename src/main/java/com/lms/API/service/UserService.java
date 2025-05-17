package com.lms.API.service;

import com.lms.API.data.User;
import com.lms.API.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        // Validate input
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || !isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Valid email is required");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty() || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }

        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Save user to database
        return userRepository.save(user);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}

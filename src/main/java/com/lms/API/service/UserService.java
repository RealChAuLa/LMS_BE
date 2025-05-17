package com.lms.API.service;

import com.lms.API.data.User;
import com.lms.API.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || !isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Valid email is required");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty() || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
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


    // Add these methods to your existing UserService class

    public User loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                user.setLastUpdatedAt(LocalDateTime.now());
                return userRepository.save(user);
            }
        }
        throw new IllegalArgumentException("Invalid email or password");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User activateDeactivateUser(Long userId, boolean isActive) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(isActive);
            user.setLastUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }

    public User resetPassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(currentPassword)) {
                user.setPassword(newPassword);
                user.setLastUpdatedAt(LocalDateTime.now());
                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Current password is incorrect");
            }
        }
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }

    public User updateUserDetails(Long userId, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Update only non-null fields
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getFullName() != null) {
                existingUser.setFullName(updatedUser.getFullName());
            }
            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }

            existingUser.setLastUpdatedAt(LocalDateTime.now());
            return userRepository.save(existingUser);
        }
        throw new IllegalArgumentException("User not found with ID: " + userId);
    }
}

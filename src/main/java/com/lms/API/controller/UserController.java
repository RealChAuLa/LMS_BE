package com.lms.API.controller;

import com.lms.API.data.User;
import com.lms.API.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
@Api(value = "User Management System", description = "Operations pertaining to user management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Register a new user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 409, message = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred during registration: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Login endpoint
    @ApiOperation(value = "Login user", notes = "Authenticates user with email and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in"),
            @ApiResponse(code = 400, message = "Invalid credentials"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        try {
            User user = userService.loginUser(email, password);
            // Create a copy of user object without password
            User userWithoutPassword = new User();
            userWithoutPassword.setId(user.getId());
            userWithoutPassword.setEmail(user.getEmail());
            userWithoutPassword.setFullName(user.getFullName());
            userWithoutPassword.setCreatedAt(user.getCreatedAt());
            userWithoutPassword.setLastUpdatedAt(user.getLastUpdatedAt());
            userWithoutPassword.setActive(user.isActive());
            userWithoutPassword.setRole(user.getRole());

            return new ResponseEntity<>(userWithoutPassword, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error during login: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all users endpoint
    @ApiOperation(value = "View list of users", notes = "Returns all users in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Activate/deactivate user endpoint
    @ApiOperation(value = "Activate or deactivate user", notes = "Changes user's active status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user status"),
            @ApiResponse(code = 400, message = "Invalid user ID"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping("/{userId}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam boolean isActive) {
        try {
            User updatedUser = userService.activateDeactivateUser(userId, isActive);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user status: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Reset password endpoint
    @ApiOperation(value = "Reset user password", notes = "Resets password if current password matches")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully reset password"),
            @ApiResponse(code = 400, message = "Invalid credentials or user ID"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping("/{userId}/password")
    public ResponseEntity<?> resetPassword(
            @PathVariable Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        try {
            User user = userService.resetPassword(userId, currentPassword, newPassword);
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error resetting password: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update user details endpoint
    @ApiOperation(value = "Update user details", notes = "Updates user information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 400, message = "Invalid user ID"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @RequestBody User user) {
        try {
            User updatedUser = userService.updateUserDetails(userId, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating user: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
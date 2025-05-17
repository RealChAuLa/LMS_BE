package com.lms.API.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@ApiModel(description = "User entity representing a system user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The auto-generated ID of the user")
    private Long id;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "Username for login", required = true)
    private String username;

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "Email address of the user", required = true)
    private String email;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Password for login)", required = true)
    private String password;

    @Column(name = "full_name")
    @ApiModelProperty(notes = "Full name of the user")
    private String fullName;

    @Column(name = "created_at")
    @ApiModelProperty(notes = "Timestamp when user was created")
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    @ApiModelProperty(notes = "Timestamp of user's last login")
    private LocalDateTime lastLogin;

    @Column(name = "is_active", nullable = false)
    @ApiModelProperty(notes = "Indicates whether the user account is active")
    private boolean isActive = true;

    // Default constructor required by JPA
    public User() {
    }

    // Constructor with required fields
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
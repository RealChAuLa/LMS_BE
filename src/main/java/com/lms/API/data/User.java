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
    @ApiModelProperty(notes = "Email address of the user", required = true)
    private String email;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Password for login)", required = true)
    private String password;

    @Column(name = "full_name")
    @ApiModelProperty(notes = "Full name of the user")
    private String fullName;

    @Column(name = "role")
    @ApiModelProperty(notes = "Role of the user in the system")
    private String role;

    @Column(name = "created_at")
    @ApiModelProperty(notes = "Timestamp when user was created")
    private LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    @ApiModelProperty(notes = "Timestamp of user profile last update")
    private LocalDateTime lastUpdatedAt;

    @Column(name = "is_active", nullable = false)
    @ApiModelProperty(notes = "Indicates whether the user account is active")
    private boolean isActive = true;

    // Default constructor required by JPA
    public User() {
    }

    // Constructor with required fields
    public User(String email, String password) {
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

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }
}
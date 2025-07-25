package com.example.taskmanager.dto;

import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String username;
    private String email;

    public UserResponse(UUID id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getters
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

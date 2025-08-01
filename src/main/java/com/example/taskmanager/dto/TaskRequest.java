package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

import com.example.taskmanager.model.Task;

// same fields as Task, except for user and completed
public class TaskRequest {

    @NotNull
    private UUID userId;
    @NotBlank
    private String title;
    private String description;
    private LocalDate dueDate;
    @NotNull
    private Task.Priority priority; // Should be LOW, MEDIUM, or HIGH


    public TaskRequest(UUID id, String title, String description, LocalDate dueDate, Task.Priority priority) {
        this.userId = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getters and setters
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Task.Priority getPriority() { return priority; }
    public void setPriority(Task.Priority priority) { this.priority = priority; }
}

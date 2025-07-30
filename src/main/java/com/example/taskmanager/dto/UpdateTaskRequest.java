package com.example.taskmanager.dto;

import com.example.taskmanager.model.Task.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

// identical to TaskRequest class except no userId; only field that can't be updated
public class UpdateTaskRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Priority priority;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
}

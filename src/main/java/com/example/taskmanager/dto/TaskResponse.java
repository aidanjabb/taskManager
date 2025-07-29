package com.example.taskmanager.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.example.taskmanager.model.Task;

// same fields as Task, except for user for some reason
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Task.Priority priority; // Should be LOW, MEDIUM, or HIGH
    private boolean completed;

    // constructor, getters
    public TaskResponse(UUID id, String title, String description, LocalDate dueDate, Task.Priority priority, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = completed;
    }
}

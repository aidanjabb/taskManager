package com.example.taskmanager.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.example.taskmanager.model.Task;

// same fields as Task, except for user for some reason
// TODO how exactly is this class enhancing our security? only diff between this and Task class is the lack of a user field in this case
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

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Task.Priority getPriority() {
        return priority;
    }

    public boolean getCompleted() {
        return completed;
    }

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getPriority(),
            task.getCompleted()
        );
    }
}

package com.example.taskmanager.controller;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.service.TaskService;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /tasks; @Valid gives automatic 400 response for invalid input
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse task = taskService.createTask(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(task);
    }

    // GET /tasks?userId=...
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(@RequestParam UUID userId) {
        List<TaskResponse> tasks = taskService.getTasksForUser(userId);
        return ResponseEntity.ok(tasks);
    }

    // PATCH /tasks/{id}/complete
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> markTaskAsComplete(@PathVariable UUID taskId) {
        TaskResponse updated = taskService.markTaskAsComplete(taskId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskRequest request) {
        TaskResponse updated = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}

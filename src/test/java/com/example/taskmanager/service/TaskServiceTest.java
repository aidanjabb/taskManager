package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.AppUser;                   
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepo;
    private UserRepository userRepo;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepo = mock(TaskRepository.class);
        userRepo = mock(UserRepository.class); 
        taskService = new TaskService(taskRepo, userRepo);
    }

    @Test
    void createTask_savesAndReturnsResponse() {
        UUID userId = UUID.randomUUID();

        TaskRequest req = new TaskRequest(userId, "New Task", "Test desc", LocalDate.now().plusDays(3), Task.Priority.HIGH);

        // if you bind tasks to a User entity:
        AppUser user = new AppUser();
        user.setId(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        // capture the task that gets saved
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);

        Task saved = new Task();
        saved.setId(UUID.randomUUID());
        saved.setTitle(req.getTitle());
        saved.setDescription(req.getDescription());
        saved.setDueDate(req.getDueDate());
        saved.setPriority(req.getPriority());
        saved.setCompleted(false);
        saved.setUser(user); // or setUserId if your model uses a UUID field

        when(taskRepo.save(any(Task.class))).thenReturn(saved);

        TaskResponse resp = taskService.createTask(req);

        verify(taskRepo).save(captor.capture());
        Task toSave = captor.getValue();
        assertEquals("New Task", toSave.getTitle());
        assertEquals(Task.Priority.HIGH, toSave.getPriority());

        assertNotNull(resp);
        assertEquals(saved.getId(), resp.getId());
        assertEquals("New Task", resp.getTitle());
        assertFalse(resp.getCompleted());
    }

    @Test
    void updateTask_updatesFieldsAndReturnsResponse() {
        UUID taskId = UUID.randomUUID();

        Task existing = new Task();
        existing.setId(taskId);
        existing.setTitle("Old");
        existing.setDescription("Old desc");
        existing.setDueDate(LocalDate.now().plusDays(1));
        existing.setPriority(Task.Priority.LOW);
        existing.setCompleted(false);

        when(taskRepo.findById(taskId)).thenReturn(Optional.of(existing));
        when(taskRepo.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        UpdateTaskRequest req = new UpdateTaskRequest();
        req.setTitle("Updated");
        req.setDescription("Updated desc");
        req.setDueDate(LocalDate.now().plusDays(5));
        req.setPriority(Task.Priority.MEDIUM);

        TaskResponse resp = taskService.updateTask(taskId, req);

        assertEquals("Updated", resp.getTitle());
        assertEquals(Task.Priority.MEDIUM, resp.getPriority());
        verify(taskRepo).save(existing);
    }

    @Test
    void updateTask_throwsWhenNotFound() {
        UUID taskId = UUID.randomUUID();
        when(taskRepo.findById(taskId)).thenReturn(Optional.empty());

        UpdateTaskRequest req = new UpdateTaskRequest();
        req.setTitle("X");
        req.setDueDate(LocalDate.now().plusDays(1));
        req.setPriority(Task.Priority.LOW);

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, req));
        verify(taskRepo, never()).save(any());
    }

    @Test
    void deleteTask_deletesWhenExists() {
        UUID taskId = UUID.randomUUID();
        Task t = new Task();
        t.setId(taskId);

        // If the service calls taskRepo.existsById(taskId), pretend the task exists and return true.
        when(taskRepo.existsById(taskId)).thenReturn(true);
        // If taskRepo.deleteById(taskId) is called, do nothing (and don’t throw).
        doNothing().when(taskRepo).deleteById(taskId);

        taskService.deleteTask(taskId);
        verify(taskRepo).deleteById(taskId);
    }

    @Test
    void deleteTask_throwsWhenNotFound() {
        UUID taskId = UUID.randomUUID();
        when(taskRepo.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
        verify(taskRepo, never()).delete(any());
    }

    @Test
    void getTasksForUser_returnsMappedResponses() {
        UUID userId = UUID.randomUUID();

        Task t = new Task();
        t.setId(UUID.randomUUID());
        t.setTitle("Read");
        t.setDescription("Book");
        t.setDueDate(LocalDate.now().plusDays(2));
        t.setPriority(Task.Priority.LOW);
        t.setCompleted(false);
        // t.setUserId(userId) or t.setUser(user)
        when(taskRepo.findByUserId(userId)).thenReturn(List.of(t));

        var list = taskService.getTasksForUser(userId);

        assertEquals(1, list.size());
        assertEquals("Read", list.get(0).getTitle());
        verify(taskRepo).findByUserId(userId);
    }
}

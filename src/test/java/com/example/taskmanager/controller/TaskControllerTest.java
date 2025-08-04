package com.example.taskmanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.UpdateTaskRequest;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.Task.Priority;
import com.example.taskmanager.service.TaskService;


@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)  // 🚨 disables security filters
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    public void testGetTasksByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        TaskResponse sampleTask = new TaskResponse(
                UUID.randomUUID(),
                "Test task",
                "testing",
                LocalDate.now(),
                Priority.MEDIUM,
                false
        );

        when(taskService.getTasksForUser(userId))
                .thenReturn(Collections.singletonList(sampleTask));

        /*
        positive testing; given valid input, check for HTTP 200 OK and correct JSON vals being returned
        */
        mockMvc.perform(get("/tasks")
                        .param("userId", userId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test task"))
                .andExpect(jsonPath("$[0].priority").value("MEDIUM"));
    }


    @Test
    void testCreateTask_Success() throws Exception {
        UUID taskId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        TaskRequest request = new TaskRequest(
            userId,
            "Buy groceries",
            "Get milk and eggs",
            LocalDate.of(2025, 8, 1),
            Priority.HIGH
        );

        TaskResponse response = new TaskResponse(
            taskId,
            request.getTitle(),
            request.getDescription(),
            request.getDueDate(),
            request.getPriority(),
            false // completed
        );

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(response);

        /*
        positive testing; given valid input, check for HTTP 201 Created and correct JSON vals being returned
        */
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(taskId.toString()))
            .andExpect(jsonPath("$.title").value("Buy groceries"))
            .andExpect(jsonPath("$.priority").value("HIGH"))
            .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void testUpdateTask_Success() throws Exception {
        UUID taskId = UUID.randomUUID();

        TaskRequest updatedRequest = new TaskRequest(taskId, "Updated Title", "Updated Description", LocalDate.now().plusDays(3), Task.Priority.HIGH);

        TaskResponse updatedResponse = new TaskResponse(
            taskId,
            updatedRequest.getTitle(),
            updatedRequest.getDescription(),
            updatedRequest.getDueDate(),
            updatedRequest.getPriority(),
            false
        );

        // tell the mocked taskService what to return when it’s called with specific arguments during the test
        when(taskService.updateTask(eq(taskId), any(UpdateTaskRequest.class))).thenReturn(updatedResponse);

        /*
        positive testing; given valid input, check for HTTP 200 OK and correct JSON vals being returned
        */
        mockMvc.perform(put("/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(taskId.toString()))
            .andExpect(jsonPath("$.title").value("Updated Title"))
            .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    // positive testing
    @Test
    void testDeleteTask_Success() throws Exception {
        UUID taskId = UUID.randomUUID();

        // No need to stub anything if deleteTask is void and doesn't throw
        doNothing().when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/tasks/{id}", taskId))
            .andExpect(status().isNoContent());
    }

    // negative testing
    @Test
    void testDeleteTask_NotFound() throws Exception {
        UUID taskId = UUID.randomUUID();

        // Pretend that the service layer throws an exception when asked to delete this task, so I can test the controller's behavior.
        doThrow(new TaskNotFoundException("Task not found"))
            .when(taskService).deleteTask(taskId);

        mockMvc.perform(delete("/tasks/{id}", taskId))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Task not found"));
    }
}


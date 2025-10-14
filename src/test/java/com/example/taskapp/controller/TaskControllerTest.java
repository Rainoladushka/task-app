package com.example.taskapp.controller;

import com.example.taskapp.model.Task;
import com.example.taskapp.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void getUserTasks_ShouldReturnUserTasks() {
        TaskController controller = new TaskController(taskService);
        Task task = new Task();
        task.setTitle("Test Task");
        when(taskService.getUserTasks(1L)).thenReturn(List.of(task));

        List<Task> result = taskController.getUserTasks(1L);

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void getPendingTasks_ShouldReturnPendingTasks() {
        Task task = new Task();
        task.setTitle("Pending Task");
        when(taskService.getPendingTasks(1L)).thenReturn(List.of(task));

        List<Task> result = taskController.getPendingTasks(1L);

        assertEquals(1, result.size());
        assertEquals("Pending Task", result.get(0).getTitle());
        verify(taskService).getPendingTasks(1L);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        Task taskToCreate = new Task();
        taskToCreate.setTitle("New Task");

        Task createdTask = new Task();
        createdTask.setId(1L);
        createdTask.setTitle("New Task");

        when(taskService.createTask(taskToCreate)).thenReturn(createdTask);

        Task result = taskController.createTask(taskToCreate);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(taskService).createTask(taskToCreate);
    }

    @Test
    void deleteTask_ShouldCallService() {
        taskController.deleteTask(1L);

        verify(taskService).deleteTask(1L);
    }
}

package com.example.taskapp.service;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        reset(taskRepository);
    }
    @Test
    void getUserTasks_ShouldReturnUserTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setUserId(1L);
        task.setTitle("Test Task");

        when(taskRepository.findByUserId(1L)).thenReturn(List.of(task));

        List<Task> result = taskService.getUserTasks(1L);

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository).findByUserId(1L);
    }

    @Test
    void getPendingTasks_ShouldReturnOnlyPendingTasks() {
        Task pendingTask = new Task();
        pendingTask.setId(1L);
        pendingTask.setUserId(1L);
        pendingTask.setStatus(TaskStatus.PENDING);

        Task completedTask = new Task();
        completedTask.setId(2L);
        completedTask.setUserId(1L);
        completedTask.setStatus(TaskStatus.COMPLETED);

        when(taskRepository.findByUserId(1L)).thenReturn(List.of(pendingTask, completedTask));

        List<Task> result = taskService.getPendingTasks(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(TaskStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        Task taskToSave = new Task();
        taskToSave.setTitle("New Task");
        taskToSave.setUserId(1L);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("New Task");
        savedTask.setUserId(1L);

        when(taskRepository.save(taskToSave)).thenReturn(savedTask);

        Task result = taskService.createTask(taskToSave);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(taskRepository).save(taskToSave);
    }

    @Test
    void deleteTask_ShouldMarkAsDeleted() {
        Task task = new Task();
        task.setId(1L);
        task.setUserId(1L);
        task.setStatus(TaskStatus.PENDING);

        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskService.deleteTask(1L);

        assertTrue(task.isDeleted());
        verify(taskRepository).save(task);
    }

    @Test
    void deleteTask_WhenTaskNotFound_ShouldDoNothing() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        taskService.deleteTask(999L);

        verify(taskRepository, never()).save(any(Task.class));
        verify(taskRepository).findById(999L);
    }
}

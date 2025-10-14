package com.example.taskapp.service;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.jpa.JpaTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private JpaTaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void getUserTasks_ShouldReturnUserTasks() {
        Task task = new Task();
        task.setUserId(1L);
        task.setTitle("Test Task");
        task.setDeleted(false);
        taskRepository.save(task);

        List<Task> result = taskService.getUserTasks(1L);

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
    }

    @Test
    void getPendingTasks_ShouldReturnOnlyPendingTasks() {
        Task pendingTask = new Task();
        pendingTask.setUserId(1L);
        pendingTask.setTitle("Pending Task");
        pendingTask.setStatus(TaskStatus.PENDING);
        pendingTask.setDeleted(false);
        taskRepository.save(pendingTask);

        Task completedTask = new Task();
        completedTask.setTitle("Completed Task");
        completedTask.setUserId(1L);
        completedTask.setStatus(TaskStatus.COMPLETED);
        completedTask.setDeleted(false);
        taskRepository.save(completedTask);

        List<Task> result = taskService.getPendingTasks(1L);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        Task taskToCreate = new Task();
        taskToCreate.setTitle("New Task");
        taskToCreate.setUserId(1L);
        taskToCreate.setDeleted(false);

        Task result = taskService.createTask(taskToCreate);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        assertTrue(result.getId() > 0);
    }

    @Test
    void deleteTask_ShouldMarkAsDeleted() {
        Task task = new Task();
        task.setTitle("Task to delete");
        task.setUserId(1L);
        task.setStatus(TaskStatus.PENDING);
        task.setDeleted(false);
        Task saved = taskRepository.save(task);

        taskService.deleteTask(saved.getId());

        Optional<Task> updated = taskRepository.findById(saved.getId());
        assertTrue(updated.isPresent());
        assertTrue(updated.get().isDeleted());
    }

    @Test
    void deleteTask_WhenTaskNotFound_ShouldDoNothing() {
        Long nonExistentId = 999L;

        taskService.deleteTask(nonExistentId);

        Optional<Task> notFound = taskRepository.findById(nonExistentId);
        assertFalse(notFound.isPresent());
    }
}

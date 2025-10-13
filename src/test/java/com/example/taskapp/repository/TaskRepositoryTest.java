package com.example.taskapp.repository;

import com.example.taskapp.model.Task;
import com.example.taskapp.repository.impl.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryTest {

    private InMemoryTaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository = new InMemoryTaskRepository();
    }

    @Test
    void save_ShouldSaveTask() {
        // Given
        Task task = new Task();
        task.setTitle("Test Task");
        task.setUserId(1L);

        // When
        Task saved = taskRepository.save(task);

        // Then
        assertNotNull(saved.getId());
        assertEquals("Test Task", saved.getTitle());
        assertEquals(1L, saved.getUserId());
    }

    @Test
    void findById_ShouldReturnTask() {
        // Given
        Task task = new Task();
        task.setTitle("Test Task");
        Task saved = taskRepository.save(task);

        // When
        Optional<Task> result = taskRepository.findById(saved.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
    }

    @Test
    void findByUserId_ShouldReturnUserTasks() {
        // Given
        Task task1 = new Task();
        task1.setUserId(1L);
        task1.setTitle("User 1 Task");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setUserId(2L);
        task2.setTitle("User 2 Task");
        taskRepository.save(task2);

        // When
        List<Task> result = taskRepository.findByUserId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("User 1 Task", result.get(0).getTitle());
    }

    @Test
    void markAsDeleted_ShouldMarkTaskAsDeleted() {
        // Given
        Task task = new Task();
        task.setTitle("Test Task");
        Task saved = taskRepository.save(task);

        // When
        taskRepository.markAsDeleted(saved.getId());

        // Then - проверяем что задача помечена как удаленная
        Optional<Task> result = taskRepository.findById(saved.getId());
        assertTrue(result.isPresent());
        assertTrue(result.get().isDeleted());
    }
}
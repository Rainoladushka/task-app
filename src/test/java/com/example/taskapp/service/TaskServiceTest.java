package com.example.taskapp.service;

import com.example.taskapp.config.RabbitMQConfig;
import com.example.taskapp.messaging.TaskCreatedMessage;
import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.jpa.JpaTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private JpaTaskRepository taskRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getUserTasks_ShouldReturnUserTasks() {
        Task task = new Task();
        task.setUserId(1L);
        task.setId(1L);
        task.setTitle("Test Task");
        when(taskRepository.findByUserIdAndDeletedFalse(1L)).thenReturn(List.of(task));

        List<Task> result = taskService.getUserTasks(1L);

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository).findByUserIdAndDeletedFalse(1L);
    }

    @Test
    void getPendingTasks_ShouldReturnOnlyPendingTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Pending Task");
        task.setUserId(1L);
        task.setStatus(TaskStatus.PENDING);
        when(taskRepository.findByUserIdAndStatusAndDeletedFalse(1L, TaskStatus.PENDING))
                .thenReturn(List.of(task));

        List<Task> result = taskService.getPendingTasks(1L);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.PENDING, result.get(0).getStatus());
        verify(taskRepository).findByUserIdAndStatusAndDeletedFalse(1L, TaskStatus.PENDING);
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        Task taskToCreate = new Task();
        taskToCreate.setTitle("New Task");
        taskToCreate.setUserId(1L);
        taskToCreate.setDeleted(false);

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("New Task");
        savedTask.setUserId(1L);
        savedTask.setDeleted(false);
        when(taskRepository.save(taskToCreate)).thenReturn(savedTask);

        Task result = taskService.createTask(taskToCreate);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        assertTrue(result.getId() > 0);
        verify(taskRepository).save(taskToCreate);
        verify(rabbitTemplate).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_TASK),
                eq(RabbitMQConfig.ROUTING_KEY_TASK_CREATED),
                any(TaskCreatedMessage.class)
        );
    }

    @Test
    void deleteTask_ShouldMarkAsDeleted() {
        Task task = new Task();
        task.setTitle("Task to delete");
        task.setUserId(1L);
        task.setStatus(TaskStatus.PENDING);
        task.setDeleted(false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        taskService.deleteTask(1L);

        assertTrue(task.isDeleted());
        verify(taskRepository).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    void deleteTask_WhenTaskNotFound_ShouldDoNothing() {
        Long nonExistentId = 999L;
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        taskService.deleteTask(nonExistentId);

        verify(taskRepository).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }
}

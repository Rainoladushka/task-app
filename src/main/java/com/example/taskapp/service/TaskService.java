package com.example.taskapp.service;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getPendingTasks(Long userId) {
        List<Task> tasks = taskRepository.findByUserId(userId);
        if (tasks == null) {
            return List.of();
        }
        return tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.PENDING)
                .toList();
    }

    public Task createTask(Task task) {
        if (task.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(task -> {
            task.setDeleted(true);
            taskRepository.save(task);
        });
    }
}


package com.example.taskapp.service;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.jpa.JpaTaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final JpaTaskRepository taskRepository;

    public TaskService(JpaTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Cacheable(value = "userTasks", key = "#userId")
    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    @Cacheable(value = "pendingTasks", key = "#userId")
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndStatusAndDeletedFalse(userId, TaskStatus.PENDING);
    }

    @CacheEvict(value = {"userTasks", "pendingTasks"}, key = "#task.userId")
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @CacheEvict(value = {"userTasks", "pendingTasks"}, key = "#userId")
    public void deleteTask(Long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setDeleted(true);
            taskRepository.save(task);
        }
    }
}
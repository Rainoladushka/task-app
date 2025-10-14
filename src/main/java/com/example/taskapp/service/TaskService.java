package com.example.taskapp.service;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.TaskRepository;
import com.example.taskapp.repository.jpa.JpaTaskRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final JpaTaskRepository taskRepository;

    public TaskService(JpaTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserIdAndDeletedFalse(userId);
    }

    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndStatusAndDeletedFalse(userId, TaskStatus.PENDING);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        Optional<Task> taskOpt = taskRepository.findById(taskId);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setDeleted(true);
            taskRepository.save(task);
        }
    }
}
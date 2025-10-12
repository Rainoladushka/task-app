package com.example.taskapp.repository;

import com.example.taskapp.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findByUserId(Long userId);
    Optional<Task> findById(Long id);
    void markAsDeleted(Long id);
}



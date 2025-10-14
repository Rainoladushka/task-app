package com.example.taskapp.repository.impl;

import com.example.taskapp.model.Task;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
//@Profile("inmemory")
public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(idCounter.getAndIncrement());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        return tasks.values().stream()
                .filter(task -> task.getUserId() != null && task.getUserId().equals(userId))
                .filter(task -> !task.isDeleted())
                .toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public void markAsDeleted(Long id) {
        Task task = tasks.get(id);
        if (task != null) {
            task.setDeleted(true);
        }
    }
}

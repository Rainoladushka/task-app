package com.example.taskapp.messaging;

import java.time.LocalDateTime;

public class TaskCreatedMessage {
    private Long taskId;
    private String title;
    private String description;
    private Long userId;
    private LocalDateTime creationDate;

    public TaskCreatedMessage() {}

    public TaskCreatedMessage(Long taskId, String title, String description, Long userId, LocalDateTime creationDate) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.creationDate = creationDate;
    }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
}
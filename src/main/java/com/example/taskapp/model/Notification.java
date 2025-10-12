package com.example.taskapp.model;

public class Notification {
    private Long id;
    private String message;
    private Long userId;
    private boolean read = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}
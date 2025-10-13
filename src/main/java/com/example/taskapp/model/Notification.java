package com.example.taskapp.model;

public class Notification {
    private Long id;
    private String message;
    private Long userId;
    private boolean sent = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public boolean isSent() { return sent; }
    public void setSent(boolean sent) { this.sent = sent; }
}
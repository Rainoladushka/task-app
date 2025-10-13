package com.example.taskapp.repository;

import com.example.taskapp.model.Notification;
import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    List<Notification> findByUserId(Long userId);
    void clear();
}


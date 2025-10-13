package com.example.taskapp.service;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .filter(notification -> !notification.isSent()) // ← важно!
                .collect(Collectors.toList());
    }
}
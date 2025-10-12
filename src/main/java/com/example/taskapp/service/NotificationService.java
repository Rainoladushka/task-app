package com.example.taskapp.service;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
        addTestData();
    }

    private void addTestData() {
        Notification notification = new Notification();
        notification.setMessage("Welcome to Task App!");
        notification.setUserId(1L);
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .filter(notification -> !notification.isRead())
                .toList();
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }
}
package com.example.taskapp.service;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.jpa.JpaNotificationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private final JpaNotificationRepository notificationRepository;

    public NotificationService(JpaNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getPendingNotifications(Long userId) {
        return notificationRepository.findPendingByUserId(userId);
    }

    public Notification createNotification(String message, Long userId) {
        Notification notification = new Notification(message, userId);
        return notificationRepository.save(notification);
    }
}
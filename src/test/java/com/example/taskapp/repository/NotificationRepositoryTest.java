package com.example.taskapp.repository;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.impl.InMemoryNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationRepositoryTest {

    private InMemoryNotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        notificationRepository = new InMemoryNotificationRepository(); // ✅ Реальная реализация
    }

    @Test
    void save_ShouldSaveNotification() {
        Notification notification = new Notification();
        notification.setMessage("Test notification");

        Notification saved = notificationRepository.save(notification);

        assertNotNull(saved.getId());
        assertEquals("Test notification", saved.getMessage());
    }

    @Test
    void findByUserId_ShouldReturnUserNotifications() {
        Notification notification = new Notification();
        notification.setUserId(1L);
        notification.setMessage("User notification");
        notificationRepository.save(notification);

        List<Notification> result = notificationRepository.findByUserId(1L);

        assertEquals(1, result.size());
        assertEquals("User notification", result.get(0).getMessage());
    }
}

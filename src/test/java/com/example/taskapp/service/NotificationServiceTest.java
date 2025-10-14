package com.example.taskapp.service;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.NotificationRepository;
import com.example.taskapp.repository.jpa.JpaNotificationRepository;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JpaNotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll(); // ✅ теперь работает
    }

    @Test
    void getUserNotifications_ShouldReturnNotifications() {
        Notification notification = new Notification();
        notification.setUserId(1L);
        notification.setMessage("Test notification");
        notification.setSent(true);
        notificationRepository.save(notification);

        List<Notification> result = notificationService.getUserNotifications(1L);

        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(n -> "Test notification".equals(n.getMessage())));
    }

    @Test
    void getPendingNotifications_ShouldReturnPendingNotifications() {
        Notification notification = new Notification();
        notification.setUserId(1L);
        notification.setMessage("Pending notification");
        notification.setSent(false);
        notificationRepository.save(notification);

        List<Notification> result = notificationService.getPendingNotifications(1L);

        assertEquals(1, result.size());
        assertEquals("Pending notification", result.get(0).getMessage());
        assertFalse(result.get(0).isSent());
    }
}
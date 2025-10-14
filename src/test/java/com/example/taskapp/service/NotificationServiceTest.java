package com.example.taskapp.service;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.jpa.JpaNotificationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private JpaNotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void getUserNotifications_ShouldReturnNotifications() {
        Notification notification = new Notification();
        notification.setUserId(1L);
        notification.setMessage("Test notification");
        when(notificationRepository.findByUserId(1L)).thenReturn(List.of(notification));

        List<Notification> result = notificationService.getUserNotifications(1L);

        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(n -> "Test notification".equals(n.getMessage())));
        verify(notificationRepository).findByUserId(1L);
    }

    @Test
    void getPendingNotifications_ShouldReturnPendingNotifications() {
        Notification notification = new Notification();
        notification.setUserId(1L);
        notification.setMessage("Pending notification");
        notification.setSent(false);
        when(notificationRepository.findByUserIdAndSentFalse(1L)).thenReturn(List.of(notification));

        List<Notification> result = notificationService.getPendingNotifications(1L);

        assertEquals(1, result.size());
        assertEquals("Pending notification", result.get(0).getMessage());
        assertFalse(result.get(0).isSent());
        verify(notificationRepository).findByUserIdAndSentFalse(1L);
    }
}

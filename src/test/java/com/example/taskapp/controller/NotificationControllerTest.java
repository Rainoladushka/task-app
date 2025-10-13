package com.example.taskapp.controller;

import com.example.taskapp.model.Notification;
import com.example.taskapp.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Test
    void getUserNotifications_ShouldReturnNotifications() {
        Notification notification = new Notification();
        notification.setMessage("Test notification");
        when(notificationService.getUserNotifications(1L)).thenReturn(List.of(notification));

        List<Notification> result = notificationController.getUserNotifications(1L);

        assertEquals(1, result.size());
        assertEquals("Test notification", result.get(0).getMessage());
        verify(notificationService).getUserNotifications(1L);
    }

    @Test
    void getPendingNotifications_ShouldReturnPendingNotifications() {
        Notification notification = new Notification();
        notification.setMessage("Pending notification");
        when(notificationService.getPendingNotifications(1L)).thenReturn(List.of(notification));

        List<Notification> result = notificationController.getPendingNotifications(1L);

        assertEquals(1, result.size());
        assertEquals("Pending notification", result.get(0).getMessage());
        verify(notificationService).getPendingNotifications(1L);
    }
}

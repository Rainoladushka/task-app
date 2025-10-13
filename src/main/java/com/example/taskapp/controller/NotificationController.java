package com.example.taskapp.controller;
import com.example.taskapp.model.Notification;
import com.example.taskapp.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/user/{userId}/pending")
    public List<Notification> getPendingNotifications(@PathVariable Long userId) {
        return notificationService.getPendingNotifications(userId);
    }
}
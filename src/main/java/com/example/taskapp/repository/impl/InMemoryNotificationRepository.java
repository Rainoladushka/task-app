package com.example.taskapp.repository.impl;

import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.NotificationRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryNotificationRepository implements NotificationRepository {
    private final Map<Long, Notification> notifications = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            notification.setId(idCounter.getAndIncrement());
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return notifications.values().stream()
                .filter(notification -> notification.getUserId().equals(userId))
                .toList();
    }
}

package com.example.taskapp.messaging;

import com.example.taskapp.config.RabbitMQConfig;
import com.example.taskapp.model.Notification;
import com.example.taskapp.repository.jpa.JpaNotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationListener {

    private final JpaNotificationRepository notificationRepository;

    public NotificationListener(JpaNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TASK_CREATED)
    public void handleTaskCreated(TaskCreatedMessage message) {
        Notification notification = new Notification();
        notification.setUserId(message.getUserId());
        notification.setMessage("New task created: " + message.getTitle());
        notification.setSent(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        System.out.println("Received message and created notification for user " + message.getUserId());
    }
}
package com.example.taskapp.service;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import com.example.taskapp.repository.jpa.JpaTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerService.class);

    private final JpaTaskRepository taskRepository;
    private final NotificationService notificationService;

    public TaskSchedulerService(JpaTaskRepository taskRepository, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    // Проверяем просроченные задачи каждые 5 минут
    @Scheduled(fixedRate = 300000)
    public void checkForOverdueTasks() {
        logger.info("Checking for overdue tasks...");
        LocalDateTime now = LocalDateTime.now();
        List<Task> overdueTasks = taskRepository.findByTargetDateBeforeAndStatusAndDeletedFalse(now, TaskStatus.PENDING);

        for (Task task : overdueTasks) {
            logger.info("Task ID {} is overdue.", task.getId());

            sendOverdueNotificationAsync(task);       }
        logger.info("Overdue task check completed.");
    }

    @Async
    public void sendOverdueNotificationAsync(Task task) {
        logger.info("Sending overdue notification for task ID {} in background.", task.getId());
        notificationService.createNotification("Task '" + task.getTitle() + "' is overdue!", task.getUserId());
        logger.info("Overdue notification sent for task ID {}.", task.getId());
    }
}
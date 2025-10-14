package com.example.taskapp.repository.jpa;

import com.example.taskapp.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndSentFalse(Long userId);
    List<Notification> findPendingByUserId(Long userId);
}
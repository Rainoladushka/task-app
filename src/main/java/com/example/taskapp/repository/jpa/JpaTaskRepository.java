package com.example.taskapp.repository.jpa;

import com.example.taskapp.model.Task;
import com.example.taskapp.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserIdAndDeletedFalse(Long userId);
    List<Task> findByUserIdAndStatusAndDeletedFalse(Long userId, TaskStatus status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Task t SET t.deleted = true WHERE t.id = :id")
    void markAsDeleted(@Param("id") Long id);
}
package com.example.taskapp.repository;

import com.example.taskapp.model.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    User findByUsername(String username);
    Optional<User> findById(Long id);
}


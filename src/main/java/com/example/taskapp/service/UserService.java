package com.example.taskapp.service;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.jpa.JpaUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }
}

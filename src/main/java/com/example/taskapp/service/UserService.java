package com.example.taskapp.service;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return user;
    }
}


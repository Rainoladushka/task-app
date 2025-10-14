package com.example.taskapp.service;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.jpa.JpaUserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final JpaUserRepository userRepository;

    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#username")
    public User findByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElse(null);
    }

    @CacheEvict(value = "users", key = "#user.username")
    public User registerUser(User user) {
        return userRepository.save(user);
    }
}

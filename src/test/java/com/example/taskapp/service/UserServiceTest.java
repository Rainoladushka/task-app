package com.example.taskapp.service;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.impl.InMemoryUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void registerUser_ShouldSaveAndReturnUser() {
        var userRepository = new InMemoryUserRepository();
        var userService = new UserService(userRepository);

        User userToSave = new User();
        userToSave.setUsername("testuser");
        userToSave.setEmail("test@example.com");

        User result = userService.registerUser(userToSave);

        assertNotNull(result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        var userRepository = new InMemoryUserRepository();
        var userService = new UserService(userRepository);

        User user = new User();
        user.setUsername("john");
        userRepository.save(user);

        User result = userService.findByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void findByUsername_WhenUserNotFound_ShouldReturnNull() {
        var userRepository = new InMemoryUserRepository();
        var userService = new UserService(userRepository);

        User result = userService.findByUsername("unknown");

        assertNull(result);
    }
}
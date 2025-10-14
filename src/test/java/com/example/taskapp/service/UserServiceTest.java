package com.example.taskapp.service;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.jpa.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JpaUserRepository userRepository;

    @Test
    void registerUser_ShouldSaveAndReturnUser() {
        User userToSave = new User();
        userToSave.setUsername("testuser");
        userToSave.setEmail("test@example.com");

        User result = userService.registerUser(userToSave);

        assertNotNull(result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@test.com");
        userRepository.save(user);

        User result = userService.findByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }

    @Test
    void findByUsername_WhenUserNotFound_ShouldReturnNull() {
        User result = userService.findByUsername("unknown");

        assertNull(result);
    }
}
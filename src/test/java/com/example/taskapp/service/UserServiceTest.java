package com.example.taskapp.service;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.jpa.JpaUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private JpaUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_ShouldSaveAndReturnUser() {
        User userToSave = new User();
        userToSave.setUsername("testuser");
        userToSave.setEmail("test@example.com");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setEmail("test@example.com");
        when(userRepository.save(userToSave)).thenReturn(savedUser);

        User result = userService.registerUser(userToSave);

        assertNotNull(result.getId());
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(userToSave);
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@test.com");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(userRepository).findByUsername("john");
    }

    @Test
    void findByUsername_WhenUserNotFound_ShouldReturnNull() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        User result = userService.findByUsername("unknown");

        assertNull(result);
        verify(userRepository).findByUsername("unknown");
    }
}
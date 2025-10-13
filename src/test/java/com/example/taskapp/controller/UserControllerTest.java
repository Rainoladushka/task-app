package com.example.taskapp.controller;

import com.example.taskapp.model.User;
import com.example.taskapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void registerUser_ShouldReturnUser() {
        // Given
        User userToRegister = new User();
        userToRegister.setUsername("testuser");

        User registeredUser = new User();
        registeredUser.setId(1L);
        registeredUser.setUsername("testuser");

        when(userService.registerUser(userToRegister)).thenReturn(registeredUser);

        User result = userController.registerUser(userToRegister);

        assertNotNull(result.getId());
        assertEquals("testuser", result.getUsername());
        }

    @Test
    void login_ShouldReturnUser() {
        User user = new User();
        user.setUsername("john");
        when(userService.findByUsername("john")).thenReturn(user);

        User result = userController.login("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
      }

    @Test
    void test_ShouldReturnServerStatus() {
        String result = userController.test();

        assertEquals("Server is working!", result);
    }
}

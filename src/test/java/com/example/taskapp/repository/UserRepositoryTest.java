package com.example.taskapp.repository;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.impl.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    void save_ShouldSaveUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("testuser", saved.getUsername());
        assertEquals("test@example.com", saved.getEmail());
    }

    @Test
    void findById_ShouldReturnUser() {
        User user = new User();
        user.setUsername("testuser");
        User saved = userRepository.save(user);

        Optional<User> result = userRepository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        User user = new User();
        user.setUsername("john");
        userRepository.save(user);

        User result = userRepository.findByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
    }
}

package com.example.taskapp.repository.impl;

import com.example.taskapp.model.User;
import com.example.taskapp.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("inmemory")
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }
        return users.values().stream()
                .filter(user -> user.getUsername() != null)
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

}

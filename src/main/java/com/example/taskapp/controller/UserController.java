package com.example.taskapp.controller;

import com.example.taskapp.model.User;
import com.example.taskapp.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/test")
    public String test() {
        return "Server is working!";
    }

    @GetMapping("/login/{username}")
    public User login(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}
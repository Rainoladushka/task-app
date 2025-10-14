package com.example.taskapp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableRabbit
public class TaskAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskAppApplication.class, args);
    }
}
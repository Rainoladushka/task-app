package com.example.taskapp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_TASK_CREATED = "task.created";
    public static final String EXCHANGE_TASK = "task.exchange";
    public static final String ROUTING_KEY_TASK_CREATED = "task.created";

    @Bean
    public Queue taskCreatedQueue() {
        return new Queue(QUEUE_TASK_CREATED, true);
    }

    @Bean
    public TopicExchange taskExchange() {
        return new TopicExchange(EXCHANGE_TASK);
    }

    @Bean
    public Binding taskCreatedBinding(Queue taskCreatedQueue, TopicExchange taskExchange) {
        return BindingBuilder.bind(taskCreatedQueue).to(taskExchange).with(ROUTING_KEY_TASK_CREATED);
    }
}
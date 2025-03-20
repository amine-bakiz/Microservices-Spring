package com.order_service.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order_exchange");
    }

    @Bean
    public Queue orderQueue() {
        return new Queue("order_queue");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with("order.created");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

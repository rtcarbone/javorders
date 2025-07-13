package com.javorders.pedidoreceiver.infrastructure.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue novoPedidoQueue() {
        return new Queue("novo-pedido-queue", true);
    }

}

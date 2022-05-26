package com.example.messages.sender;

import com.example.dto.ReviewPriorityDTO;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;
    private final Exchange appExchange;

    @Value("${rabbitmq.review-routingKey}")
    private String reviewRoutingKey;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, Exchange appExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.appExchange = appExchange;
    }

    public void send(List<ReviewPriorityDTO> message) {
        rabbitTemplate.convertAndSend(appExchange.getName(), reviewRoutingKey, message);
    }
}

package com.example.messages;

import com.example.dto.ReviewPriorityDTO;
import com.example.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmsReceiver {

    private final PriorityService priorityService;

    @Autowired
    public JmsReceiver(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @JmsListener(destination = "review-queue")
    public void receiveMessage(@Payload List<ReviewPriorityDTO> message) {
        priorityService.updatePriorities(message);
    }
}
package com.example.messages;

import com.example.dto.ReviewPriorityDTO;
import com.example.service.PriorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JmsReceiver {

    private final PriorityService priorityService;

    @Autowired
    public JmsReceiver(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @JmsListener(destination = "review-queue")
    public void receiveMessage(@Payload List<ReviewPriorityDTO> message) {
        log.info("PriorityApp received message: {}", message);
        if(message.size() == 0){
            log.info("Nothing to change, message is empty");
            return;
        }
        priorityService.updatePriorities(message);
    }
}
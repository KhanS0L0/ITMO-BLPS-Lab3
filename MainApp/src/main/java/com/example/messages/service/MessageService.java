package com.example.messages.service;

import com.example.dto.ReviewPriorityDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.review.TemporaryReview;
import com.example.mapper.ReviewPriorityMapper;
import com.example.messages.sender.RabbitMQSender;
import com.example.repository.review.TemporaryReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MessageService {
    private final RabbitMQSender rabbitMQSender;
    private final TemporaryReviewRepository repository;
    private final ReviewPriorityMapper mapper;

    @Autowired
    public MessageService(RabbitMQSender rabbitMQSender, TemporaryReviewRepository repository, ReviewPriorityMapper mapper) {
        this.rabbitMQSender = rabbitMQSender;
        this.repository = repository;
        this.mapper = mapper;
    }

    //second minute hour day_month month day_week
    //@Scheduled(cron = "* 45 * * * *", zone = "Europe/Moscow")
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void sendTemporaryReviews(){
        log.info("Current date and time is - {}", new Date());
        List<TemporaryReview> entities = repository.findAll();
        if(entities.size() == 0) return;
        List<ReviewPriorityDTO> message = new ArrayList<>();
        entities.stream()
                .filter(temporaryReview -> !temporaryReview.getStatus().equals(ReviewStatus.REJECTED.name()))
                .forEach(entity -> message.add(mapper.mapEntityToDTO(entity)));
        log.info("MainApp sent message: {}", message);
        rabbitMQSender.send(message);
    }
}

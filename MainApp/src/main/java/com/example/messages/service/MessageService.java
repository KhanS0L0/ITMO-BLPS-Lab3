package com.example.messages.service;

import com.example.dto.ReviewPriorityDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.review.TemporaryReview;
import com.example.mapper.ReviewPriorityMapper;
import com.example.messages.sender.RabbitMQSender;
import com.example.repository.review.TemporaryReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        System.out.println(new Date());
        System.out.println("<<< SCHEDULER  WORKING >>>");
        List<TemporaryReview> entities = repository.findAll();
        List<ReviewPriorityDTO> message = new ArrayList<>();
        entities.stream()
                .filter(temporaryReview -> !temporaryReview.getStatus().equals(ReviewStatus.REJECTED.name()))
                .forEach(entity -> message.add(mapper.mapEntityToDTO(entity)));

        rabbitMQSender.send(message);
    }

}

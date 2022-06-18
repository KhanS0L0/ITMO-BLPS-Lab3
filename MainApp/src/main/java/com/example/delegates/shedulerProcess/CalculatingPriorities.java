package com.example.delegates.shedulerProcess;

import com.example.dto.ReviewPriorityDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.review.TemporaryReview;
import com.example.mapper.ReviewPriorityMapper;
import com.example.messages.sender.RabbitMQSender;
import com.example.messages.service.MessageService;
import com.example.repository.review.TemporaryReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CalculatingPriorities implements JavaDelegate {

    private final RabbitMQSender rabbitMQSender;
    private final TemporaryReviewRepository repository;
    private final ReviewPriorityMapper mapper;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
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

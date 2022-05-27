package com.example.service;

import com.example.dto.ReviewPriorityDTO;
import com.example.repository.TemporaryReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PriorityServiceImpl implements PriorityService {

    private final TemporaryReviewRepository repository;

    @Autowired
    public PriorityServiceImpl(TemporaryReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void updatePriorities(List<ReviewPriorityDTO> message) {
        Timestamp currentTime = new Timestamp(new Date().getTime());
        long highPeriod = TimeUnit.DAYS.toMillis(3);
        long mediumPeriod = TimeUnit.DAYS.toMillis(2);
        long lowPeriod = TimeUnit.DAYS.toMillis(1);

        log.info("Before the priority permutation: {}", message);
        for(ReviewPriorityDTO dto: message){
            long period = currentTime.getTime() - dto.getUpdatedDate().getTime();
            if(period >= highPeriod && !dto.getPriority().equals("HIGH")){
                dto.setPriority("HIGH");
                repository.updatePriorityOfTemporaryReview(dto.getPriority(), dto.getReviewId());
            }else if(period >= mediumPeriod && !dto.getPriority().equals("MEDIUM")){
                dto.setPriority("MEDIUM");
                repository.updatePriorityOfTemporaryReview(dto.getPriority(), dto.getReviewId());
            }else if(period >= lowPeriod && !dto.getPriority().equals("LOW")){
                dto.setPriority("LOW");
                repository.updatePriorityOfTemporaryReview(dto.getPriority(), dto.getReviewId());
            }
        }
        log.info("After the priority permutation: {}", message);
    }
}

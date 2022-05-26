package com.example.service;

import com.example.dto.ReviewPriorityDTO;
import com.example.repository.TemporaryReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        for(ReviewPriorityDTO dto: message){
            long period = currentTime.getTime() - dto.getUpdatedDate().getTime();
            if(period >= highPeriod){
                dto.setPriority("HIGH");
            }else if(period >= mediumPeriod){
                dto.setPriority("MEDIUM");
            }else if(period >= lowPeriod){
                dto.setPriority("LOW");
            }
            repository.updatePriorityOfTemporaryReview(dto.getPriority(), dto.getReviewId());
        }
        //todo: in main app change sort of temp review arraylist in method getAllByAdmin
    }
}

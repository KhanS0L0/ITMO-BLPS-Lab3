package com.example.delegates.userProcess;

import com.example.dto.TemporaryReviewDTO;
import com.example.entity.review.TemporaryReview;
import com.example.service.implementation.review.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendingReviewToCheck implements JavaDelegate {
    private final ReviewServiceImpl reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String country = (String) delegateExecution.getVariable("country");
        String city = (String) delegateExecution.getVariable("city");
        String advantages = (String) delegateExecution.getVariable("advantages");
        String disadvantages = (String) delegateExecution.getVariable("disadvantages");
        String review = (String) delegateExecution.getVariable("review");
        String conclusion = (String) delegateExecution.getVariable("conclusion");
        String username = (String) delegateExecution.getVariable("username");

        TemporaryReviewDTO temporaryReview = TemporaryReviewDTO.builder()
                .country(country)
                .city(city)
                .advantages(advantages)
                .disadvantages(disadvantages)
                .reviewBody(review)
                .conclusion(conclusion)
                .userLogin(username)
                .build();

        long reviewId = reviewService.saveNewTemporaryReviewForCamunda(temporaryReview);
        delegateExecution.setVariable("reviewId", reviewId);
    }
}

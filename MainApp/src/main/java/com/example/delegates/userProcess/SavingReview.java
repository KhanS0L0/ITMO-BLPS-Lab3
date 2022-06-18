package com.example.delegates.userProcess;

import com.example.entity.review.TemporaryReview;
import com.example.service.implementation.review.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavingReview implements JavaDelegate {

    private final ReviewServiceImpl reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long reviewId = (Long) delegateExecution.getVariable("reviewId");
        TemporaryReview temporaryReview = reviewService.getTemporaryReviewById(reviewId);
        reviewService.savePublishedReview(temporaryReview);
    }

}

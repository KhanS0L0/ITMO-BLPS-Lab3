package com.example.delegates.adminProcess;

import com.example.entity.enums.ReviewStatus;
import com.example.service.implementation.review.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavingReviewStatusResult implements JavaDelegate {

    private final ReviewServiceImpl reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String status = (String) delegateExecution.getVariable("status");
        String reviewId = (String) delegateExecution.getVariable("reviewId");
        ReviewStatus reviewStatus = ReviewStatus.of(status);
        reviewService.updateTemporaryReviewStatusForCamunda(reviewStatus, Long.valueOf(reviewId));
    }

}

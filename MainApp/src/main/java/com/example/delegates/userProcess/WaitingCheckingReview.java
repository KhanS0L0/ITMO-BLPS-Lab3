package com.example.delegates.userProcess;

import com.example.entity.enums.ReviewStatus;
import com.example.service.implementation.review.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WaitingCheckingReview implements JavaDelegate {
    private final ReviewServiceImpl reviewService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        long reviewId = (Long) delegateExecution.getVariable("reviewId");

        if (!hasReviewStatus(reviewId)) {
            delegateExecution.setVariable("hasStatus", false);
            return;
        }

        ReviewStatus reviewStatus = getReviewStatus(reviewId);
        if (reviewStatus.equals(ReviewStatus.ACCEPTED))
            delegateExecution.setVariable("checking", true);
        else
            delegateExecution.setVariable("checking", false);
        delegateExecution.setVariable("hasStatus", true);

    }


    private boolean hasReviewStatus(long reviewId) {
        return ! getReviewStatus(reviewId).equals(ReviewStatus.ON_REVISION);
    }

    private ReviewStatus getReviewStatus(long reviewId) {
        return reviewService.getTemporaryReviewById(reviewId).getReviewStatus();
    }

}

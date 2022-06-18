package com.example.service.interfaces.review;

import com.example.dto.TemporaryReviewDTO;
import com.example.entity.enums.ReviewStatus;
import com.example.entity.review.TemporaryReview;
import com.example.exception.UserNotFoundException;

public interface ReviewServiceCamunda {

    long saveNewTemporaryReviewForCamunda(TemporaryReviewDTO dto) throws UserNotFoundException;

    void updateTemporaryReviewStatusForCamunda(ReviewStatus reviewStatus, Long reviewId);

}

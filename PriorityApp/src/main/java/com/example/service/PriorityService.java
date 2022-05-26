package com.example.service;

import com.example.dto.ReviewPriorityDTO;
import com.example.entity.review.TemporaryReview;

import java.util.List;

public interface PriorityService {

    public void updatePriorities(List<ReviewPriorityDTO> message);
}

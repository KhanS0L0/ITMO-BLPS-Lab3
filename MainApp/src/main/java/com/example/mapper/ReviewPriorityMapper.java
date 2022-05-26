package com.example.mapper;

import com.example.dto.ReviewPriorityDTO;
import com.example.entity.review.TemporaryReview;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewPriorityMapper {

    public ReviewPriorityDTO mapEntityToDTO(TemporaryReview review){
        ReviewPriorityDTO dto = new ReviewPriorityDTO();
        dto.setReviewId(review.getId());
        dto.setPriority(review.getPriority());
        dto.setUpdatedDate(review.getUpdatedDate());
        return dto;
    }

    public List<ReviewPriorityDTO> mapEntityListToDTOList(List<TemporaryReview> entities){
        List<ReviewPriorityDTO> dtoList = new ArrayList<>();
        entities.forEach(entity -> dtoList.add(mapEntityToDTO(entity)));
        return dtoList;
    }
}
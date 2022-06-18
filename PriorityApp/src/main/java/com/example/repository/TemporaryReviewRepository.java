package com.example.repository;

import com.example.entity.review.TemporaryReview;
import com.example.entity.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemporaryReviewRepository extends JpaRepository<TemporaryReview, Long> {
    @Modifying
    @Query("update TEMPORARY_REVIEWS tr set tr.priority = :priority where tr.id = :reviewId")
    void updatePriorityOfTemporaryReview(@Param("priority") String priority, @Param("reviewId") Long reviewId);
}

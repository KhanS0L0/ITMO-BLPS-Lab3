package com.example.entity.review;

import com.example.entity.enums.ReviewStatus;
import com.example.entity.user.Administrator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "TEMPORARY_REVIEWS")
public class TemporaryReview extends Review {

    @Column(name = "REVIEW_STATUS", nullable = false)
    private String status;

    @Column(name = "REVIEW_PRIORITY", nullable = false)
    private String priority;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE", nullable = false)
    private Timestamp updatedDate;

    @Transient
    private ReviewStatus reviewStatus;

    @ManyToOne
    @JoinColumn(name = "INSPECTOR_ID", referencedColumnName = "ID", nullable = false)
    private Administrator inspector;

    @PrePersist
    public void prePersist() {
        if (reviewStatus != null) {
            status = reviewStatus.name();
        }
    }

    @PostLoad
    public void postLoad() {
        if (status != null) {
            reviewStatus = ReviewStatus.of(status);
        }
    }

}

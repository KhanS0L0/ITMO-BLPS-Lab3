package com.example.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TemporaryReviewDTO implements Comparable<TemporaryReviewDTO> {
    private String priority;
    private Long reviewId;
    private String country;
    private String city;
    private String reviewBody;
    private String advantages;
    private String disadvantages;
    private String conclusion;
    private String userLogin;


    @Override
    public int compareTo(TemporaryReviewDTO o) {
        String p1 = this.getPriority();
        String p2 = o.getPriority();
        if(p1 == null) return 1;
        if(p2 == null) return -1;
        if(p1.equals(p2)) return 0;
        if(p1.equals("LOW") && (p2.equals("MEDIUM") || p2.equals("HIGH")))
            return -1;
        if(p1.equals("MEDIUM") && p2.equals("HIGH"))
            return -1;
        return 1;
    }
}
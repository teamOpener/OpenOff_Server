package com.example.openoff.domain.user.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class Birth {
    private Integer year;  // Integer로 변경
    private Integer month; // Integer로 변경
    private Integer day;   // Integer로 변경
    @Column(name = "is_adult")
    private Boolean isAdult;  // Boolean으로 변경

    @Builder
    public Birth(Integer year, Integer month, Integer day, Boolean isAdult) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isAdult = false;
    }

    public static Birth makeBirth(Integer year, Integer month, Integer day) {
        return Birth.builder()
                .year(year)
                .month(month)
                .day(day)
                .isAdult(false)
                .build();
    }
}

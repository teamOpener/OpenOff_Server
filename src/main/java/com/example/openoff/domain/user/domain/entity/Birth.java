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
    private int year;
    private int month;
    private int day;
    @Column(name = "is_adult")
    private boolean isAdult;

    @Builder
    public Birth(int year, int month, int day, boolean isAdult) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isAdult = false;
    }

    public static Birth makeBirth(int year, int month, int day){
        return Birth.builder()
                .year(year)
                .month(month)
                .day(day)
                .isAdult(false)
                .build();
    }
}

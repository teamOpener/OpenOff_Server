package com.example.openoff.domain.eventInstance.infrastructure.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventIndexStatisticsDto {
    private Long eventIndexId;
    private Integer approvedUserCount;
    private LocalDateTime eventDate;
    private Boolean isApply;

    @QueryProjection
    public EventIndexStatisticsDto(Long eventIndexId, Integer approvedUserCount, LocalDateTime eventDate, Boolean isApply) {
        this.eventIndexId = eventIndexId;
        this.approvedUserCount = approvedUserCount;
        this.eventDate = eventDate;
        this.isApply = isApply;
    }
}

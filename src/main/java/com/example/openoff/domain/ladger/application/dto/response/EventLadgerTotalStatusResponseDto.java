package com.example.openoff.domain.ladger.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventLadgerTotalStatusResponseDto {
    private Long eventIndexId;
    private LocalDateTime eventDate;
    private Boolean isClosed;
    private Integer maxCount;
    private Long notApprovedCount;
    private Long approvedCount;
    private Long joinedCount;

    @Builder
    public EventLadgerTotalStatusResponseDto(Long eventIndexId, LocalDateTime eventDate, Boolean isClosed, Integer maxCount, Long notApprovedCount, Long approvedCount, Long joinedCount) {
        this.eventIndexId = eventIndexId;
        this.eventDate = eventDate;
        this.isClosed = isClosed;
        this.maxCount = maxCount;
        this.notApprovedCount = notApprovedCount;
        this.approvedCount = approvedCount;
        this.joinedCount = joinedCount;
    }
}

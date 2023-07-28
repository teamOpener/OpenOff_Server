package com.example.openoff.domain.eventInstance.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MainTapEventInfoResponse {
    private Long eventInfoId;
    private String eventTitle;
    private String streetRoadAddress;
    private Integer totalApplicantCount;
    private LocalDateTime eventDate;
    private String mainImageUrl;
    private Boolean isBookmarked;

    @Builder
    public MainTapEventInfoResponse(Long eventInfoId, String eventTitle, String streetRoadAddress, Integer totalApplicantCount, LocalDateTime eventDate, String mainImageUrl, Boolean isBookmarked) {
        this.eventInfoId = eventInfoId;
        this.eventTitle = eventTitle;
        this.streetRoadAddress = streetRoadAddress;
        this.totalApplicantCount = totalApplicantCount;
        this.eventDate = eventDate;
        this.mainImageUrl = mainImageUrl;
        this.isBookmarked = isBookmarked;
    }
}

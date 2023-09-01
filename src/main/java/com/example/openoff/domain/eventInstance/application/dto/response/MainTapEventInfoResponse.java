package com.example.openoff.domain.eventInstance.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MainTapEventInfoResponse {
    private Long eventInfoId;
    private String eventTitle;
    private String streetRoadAddress;
    private Integer totalApplicantCount;
    private String eventDate;
    private List<FieldType> fieldTypes;
    private String mainImageUrl;
    private Boolean isBookmarked;

    @Builder
    public MainTapEventInfoResponse(Long eventInfoId, String eventTitle, String streetRoadAddress, Integer totalApplicantCount, List<FieldType> fieldTypes, String eventDate, String mainImageUrl, Boolean isBookmarked) {
        this.eventInfoId = eventInfoId;
        this.eventTitle = eventTitle;
        this.streetRoadAddress = streetRoadAddress;
        this.totalApplicantCount = totalApplicantCount;
        this.fieldTypes = fieldTypes;
        this.eventDate = eventDate;
        this.mainImageUrl = mainImageUrl;
        this.isBookmarked = isBookmarked;
    }
}

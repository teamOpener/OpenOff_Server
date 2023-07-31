package com.example.openoff.domain.bookmark.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyBookmarkEventResponseDto {
    private Long bookmarkId;
    private Long eventInfoId;
    private String eventTitle;
    private String streetRoadAddress;
    private List<FieldType> fieldTypeList;
    private List<LocalDateTime> eventDateList;
    private String eventMainImageUrl;
    private Integer totalApplicantCount;

    @Builder
    public MyBookmarkEventResponseDto(Long bookmarkId, Long eventInfoId, String eventTitle, String streetRoadAddress, List<FieldType> fieldTypeList, List<LocalDateTime> eventDateList, String eventMainImageUrl, Integer totalApplicantCount) {
        this.bookmarkId = bookmarkId;
        this.eventInfoId = eventInfoId;
        this.eventTitle = eventTitle;
        this.streetRoadAddress = streetRoadAddress;
        this.fieldTypeList = fieldTypeList;
        this.eventDateList = eventDateList;
        this.eventMainImageUrl = eventMainImageUrl;
        this.totalApplicantCount = totalApplicantCount;
    }
}

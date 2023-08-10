package com.example.openoff.domain.eventInstance.application.dto.response;

import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateNewEventResponseDto {
    private CreateNewEventRequestDto createDataInfo;
    private Long eventInfoId;
    private List<Long> eventIndexIdList;
    private List<Long> eventImageIdList;
    private List<Long> eventQuestionIdList;
    private List<Long> eventInterestFieldIdList;
    private List<Long> eventStaffIds;

    @Builder
    public CreateNewEventResponseDto(CreateNewEventRequestDto createDataInfo, Long eventInfoId, List<Long> eventIndexIdList, List<Long> eventImageIdList, List<Long> eventQuestionIdList, List<Long> eventInterestFieldIdList, List<Long> eventStaffIds) {
        this.createDataInfo = createDataInfo;
        this.eventInfoId = eventInfoId;
        this.eventIndexIdList = eventIndexIdList;
        this.eventImageIdList = eventImageIdList;
        this.eventQuestionIdList = eventQuestionIdList;
        this.eventInterestFieldIdList = eventInterestFieldIdList;
        this.eventStaffIds = eventStaffIds;
    }
}

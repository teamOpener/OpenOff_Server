package com.example.openoff.domain.eventInstance.application.mapper;

import com.example.openoff.common.annotation.Mapper;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.CreateNewEventResponseDto;

import java.util.List;

@Mapper
public class EventInstanceMapper {

    public static CreateNewEventResponseDto mapToEventInstanceInfoResponse(CreateNewEventRequestDto createDataInfo, Long eventInfoId, List<Long> eventIndexIdList, List<Long> eventImageIdList, List<Long> eventQuestionIdList, List<Long> eventInterestFieldIdList, Long eventStaffId){
        return CreateNewEventResponseDto.builder()
                .createDataInfo(createDataInfo)
                .eventInfoId(eventInfoId)
                .eventIndexIdList(eventIndexIdList)
                .eventImageIdList(eventImageIdList)
                .eventQuestionIdList(eventQuestionIdList)
                .eventInterestFieldIdList(eventInterestFieldIdList)
                .eventStaffId(eventStaffId)
                .build();
    }
}

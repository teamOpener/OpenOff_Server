package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.CreateNewEventResponseDto;
import com.example.openoff.domain.eventInstance.application.mapper.EventInstanceMapper;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventExtraQuestionService;
import com.example.openoff.domain.eventInstance.domain.service.EventImageService;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.interest.domain.exception.TooManyFieldException;
import com.example.openoff.domain.interest.domain.service.FieldService;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class EventCreateUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventIndexService eventIndexService;
    private final EventImageService eventImageService;
    private final EventExtraQuestionService eventExtraQuestionService;
    private final FieldService fieldService;
    private final EventStaffService eventStaffService;


    public CreateNewEventResponseDto createEvent(CreateNewEventRequestDto createNewEventRequestDto) {
        if(createNewEventRequestDto.getFieldTypeList().size() >= 4) {
            throw TooManyFieldException.of(Error.TOO_MANY_EVENT_FIELD);
        }
        final User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.saveEventInfo(createNewEventRequestDto);
        List<Long> eventIndexIdList = eventIndexService.saveEventIndex(eventInfo, createNewEventRequestDto.getEventStartDate(), createNewEventRequestDto.getEventEndDate());
        List<Long> eventImageIdList = eventImageService.saveEventImage(eventInfo, createNewEventRequestDto.getImageDataList());
        List<Long> eventExtraQuestionIdList = eventExtraQuestionService.saveEventExtraQuestion(eventInfo, createNewEventRequestDto.getExtraQuestionList());
        List<Long> eventInterestFieldIdList = fieldService.saveEventInterestFields(eventInfo, createNewEventRequestDto.getFieldTypeList());
        Long eventStaffId = eventStaffService.saveEventStaff(user, eventInfo, createNewEventRequestDto.getHostPhoneNumber(), createNewEventRequestDto.getHostEmail(), createNewEventRequestDto.getHostName());

        return EventInstanceMapper.mapToEventInstanceInfoResponse(createNewEventRequestDto, eventInfo.getId(), eventIndexIdList, eventImageIdList, eventExtraQuestionIdList, eventInterestFieldIdList, eventStaffId);
    }
}

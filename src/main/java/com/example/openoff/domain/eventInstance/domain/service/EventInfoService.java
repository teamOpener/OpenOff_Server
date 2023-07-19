package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventInfoService {
    private final EventInfoRepository eventInfoRepository;

    public EventInfo saveEventInfo(CreateNewEventRequestDto createNewEventRequestDto) {
        // TODO: createNewEventRequestDto.getStreetLoadAddress() 로 위,경도 구하는 로직 있어야함
        EventInfo eventInfo = EventInfo.toEntity(
                createNewEventRequestDto.getTitle(),
                createNewEventRequestDto.getEventFee(),
                createNewEventRequestDto.getMaxParticipant(),
                createNewEventRequestDto.getDescription(),
                createNewEventRequestDto.getApplicationStartDate(),
                createNewEventRequestDto.getApplicationEndDate(),
                11.11, 22.22, createNewEventRequestDto.getStreetLoadAddress(), createNewEventRequestDto.getDetailAddress());
        return eventInfoRepository.save(eventInfo);
    }
}

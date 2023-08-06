package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepository;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
                37.498020, 126.026702, createNewEventRequestDto.getStreetLoadAddress(), createNewEventRequestDto.getDetailAddress());
        return eventInfoRepository.save(eventInfo);
    }

    public List<EventInfo> getEventMapList(EventSearchRequestDto eventSearchRequestDto){
        return eventInfoRepository.searchAllEventInfosInMap(eventSearchRequestDto);
    }

    public Page<EventInfo> getMainTapEventByField(FieldType fieldType, Long eventInfoId, Pageable pageable){
        return eventInfoRepository.findMainTapEventInfoByFieldType2(fieldType, eventInfoId, pageable);
    }

    public Page<EventInfo> getMainTapEventByVogue(Long eventInfoId, Integer count, Pageable pageable){
        return eventInfoRepository.findMainTapEventInfoByVogue2(eventInfoId, count, pageable);
    }

    public Page<EventInfo> getHostEventList(String userId, Long eventInfoId, FieldType fieldType, Pageable pageable){
        return eventInfoRepository.findHostEventInfo(userId, eventInfoId, fieldType, pageable);
    }

    public EventInfo findEventInfoById(Long eventInfoId){
        return eventInfoRepository.findById(eventInfoId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
}

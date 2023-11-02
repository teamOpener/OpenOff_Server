package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.KakaoAddressResponse;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepository;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventInfoService {
    private final EventInfoRepository eventInfoRepository;

    public EventInfo saveEventInfo(CreateNewEventRequestDto createNewEventRequestDto, KakaoAddressResponse coord) {
        EventInfo eventInfo = EventInfo.toEntity(
                createNewEventRequestDto.getTitle(),
                createNewEventRequestDto.getEventFee(),
                createNewEventRequestDto.getMaxParticipant(),
                createNewEventRequestDto.getDescription(),
                createNewEventRequestDto.getApplicationStartDate(),
                createNewEventRequestDto.getApplicationEndDate(),
                Double.valueOf(coord.getDocuments().get(0).getY()),
                Double.valueOf(coord.getDocuments().get(0).getX()),
                createNewEventRequestDto.getStreetLoadAddress(), createNewEventRequestDto.getDetailAddress());
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

    public List<EventInfo> findEventInfoByMyInterestFields(List<FieldType> fieldTypes) {
        return eventInfoRepository.findEventInfosByFieldTypes(fieldTypes);
    }

    public void suspensionEventApplication(EventInfo eventInfo) {
        eventInfo.updateApplyPermit(false);
        eventInfoRepository.save(eventInfo);
    }

    public void resumeEventApplication(EventInfo eventInfo) {
        eventInfo.updateApplyPermit(true);
        eventInfoRepository.save(eventInfo);
    }

    public void minusTotalRegisterCount(EventInfo eventInfo) {
        eventInfo.updateTotalRegisterCountMinus();
        eventInfoRepository.save(eventInfo);
    }

    public void deleteEventInfo(Long eventInfoId){
        EventInfo eventInfo = eventInfoRepository.findById(eventInfoId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
        eventInfoRepository.delete(eventInfo);
    }

    public List<Long> getOpenEventIdList() {
        return eventInfoRepository.findOpenEventInfoIds();
    }
}

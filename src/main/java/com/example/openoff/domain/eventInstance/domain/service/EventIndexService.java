package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventIndexService {
    private final EventIndexRepository eventIndexRepository;

    public List<Long> saveEventIndex(EventInfo eventInfo, List<LocalDateTime> eventDates) {
        List<EventIndex> eventIndexList = eventDates.stream()
                .map(date -> EventIndex.toEntity(eventInfo, date))
                .collect(Collectors.toList());
        return eventIndexRepository.saveAll(eventIndexList).stream().map(EventIndex::getId).collect(Collectors.toList());
    }

    public List<EventIndexStatisticsDto> getEventDetailInfo(Long eventInfoId, String userId){
        return eventIndexRepository.statisticsEventIndexByEventInfoId(eventInfoId, userId);
    }

    public EventIndex findById(Long eventIndexId) {
        return eventIndexRepository.findById(eventIndexId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
}

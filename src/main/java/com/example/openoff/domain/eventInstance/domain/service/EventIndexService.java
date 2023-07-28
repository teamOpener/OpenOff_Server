package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventIndexService {
    private final EventIndexRepository eventIndexRepository;

    public List<Long> saveEventIndex(EventInfo eventInfo, LocalDateTime eventStartDate, LocalDateTime eventEndDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(eventStartDate, eventEndDate);
        List<EventIndex> eventIndexList = Stream.iterate(eventStartDate, date -> date.plusDays(1))
                .limit(numOfDaysBetween + 1)
                .collect(Collectors.toList()).stream()
                .map(date -> EventIndex.toEntity(eventInfo, date))
                .collect(Collectors.toList());
        return eventIndexRepository.saveAll(eventIndexList).stream().map(EventIndex::getId).collect(Collectors.toList());
    }

    public EventIndex findById(Long eventIndexId) {
        return eventIndexRepository.findById(eventIndexId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
}

package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventIndexRepositoryCustom {
    List<EventIndexStatisticsDto> statisticsEventIndexByEventInfoId(Long eventInfoId, String userId);
    Map<Long, LocalDateTime> findEventDateByEventInfoId(List<Long> eventInfoIdList);
}

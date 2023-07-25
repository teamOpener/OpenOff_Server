package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;

import java.util.List;

public interface EventIndexRepositoryCustom {
    List<EventIndexStatisticsDto> statisticsEventIndexByEventInfoId(Long eventInfoId, String userId);
}

package com.example.openoff.domain.eventInstance.domain.repository;

import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;

import java.util.List;

public interface EventInfoRepositoryCustom {
    List<EventInfo> searchAllEventInfosInMap(EventSearchRequestDto eventSearchRequestDto);
}

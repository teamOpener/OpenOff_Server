package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.DetailEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.SearchMapEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.mapper.EventInstanceMapper;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepository;
import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventSearchUseCase {
    private final UserUtils userUtils;
    private final EventInfoRepository eventInfoRepository;
    private final EventIndexRepository eventIndexRepository;


    public List<SearchMapEventInfoResponseDto> searchMapEventInfo(EventSearchRequestDto eventSearchRequestDto)
    {
        userUtils.getUser();
        List<EventInfo> eventInfoList = eventInfoRepository.searchAllEventInfosInMap(eventSearchRequestDto);
        return EventInstanceMapper.mapToSearchMapEventInfoResponseList(eventInfoList);
    }

    public DetailEventInfoResponseDto getDetailEventInfo(Long eventInfoId){
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoRepository.findById(eventInfoId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        List<EventIndexStatisticsDto> eventIndexStatisticsDtos = eventIndexRepository.statisticsEventIndexByEventInfoId(eventInfoId, user.getId());
        return EventInstanceMapper.mapToDetailEventInfoResponse(eventInfo, eventIndexStatisticsDtos);
    }
}

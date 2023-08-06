package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.DetailEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.HostEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.MainTapEventInfoResponse;
import com.example.openoff.domain.eventInstance.application.dto.response.SearchMapEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.mapper.EventInstanceMapper;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.interest.domain.entity.UserInterestField;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventSearchUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventIndexService eventIndexService;


    public List<SearchMapEventInfoResponseDto> searchMapEventInfo(EventSearchRequestDto eventSearchRequestDto)
    {
        userUtils.getUser();
        List<EventInfo> eventMapList = eventInfoService.getEventMapList(eventSearchRequestDto);
        return EventInstanceMapper.mapToSearchMapEventInfoResponseList(eventMapList);
    }

    public DetailEventInfoResponseDto getDetailEventInfo(Long eventInfoId){
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        List<EventIndexStatisticsDto> eventDetailInfo = eventIndexService.getEventDetailInfo(eventInfo.getId(), user.getId());
        return EventInstanceMapper.mapToDetailEventInfoResponse(eventInfo, eventDetailInfo);
    }

    public List<MainTapEventInfoResponse> getPersonalEventInfoList(){
        User user = userUtils.getUser();
        List<FieldType> myInterests = user.getUserInterestFields().stream().map(UserInterestField::getFieldType).collect(Collectors.toList());
        List<EventInfo> eventInfoByMyInterestFields = eventInfoService.findEventInfoByMyInterestFields(myInterests);
        return EventInstanceMapper.mapToMainTapEventInfoResponseList(eventInfoByMyInterestFields);
    }

    public PageResponse<MainTapEventInfoResponse> getMainTapList(Long eventInfoId, FieldType fieldType, Integer count, Pageable pageable) {
        User user = userUtils.getUser();
        if (fieldType != null) {
            Page<EventInfo> mainTapEventByField = eventInfoService.getMainTapEventByField(fieldType, eventInfoId, pageable);
            return EventInstanceMapper.mapToMainTapEventInfoResponse(mainTapEventByField);
        } else {
            Page<EventInfo> mainTapEventByField = eventInfoService.getMainTapEventByVogue(eventInfoId, count, pageable);
            return EventInstanceMapper.mapToMainTapEventInfoResponse(mainTapEventByField);
        }
    }

    public PageResponse<HostEventInfoResponseDto> getHostEventInfoList(Long eventInfoId, FieldType fieldType, Pageable pageable) {
        User user = userUtils.getUser();
        Page<EventInfo> hostEventList = eventInfoService.getHostEventList(user.getId(), eventInfoId, fieldType, pageable);
        return EventInstanceMapper.mapToHostEventInfoResponseList(hostEventList);
    }
}

package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.bookmark.domain.repository.EventBookmarkRepository;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.DetailEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.MainTapEventInfoResponse;
import com.example.openoff.domain.eventInstance.application.dto.response.SearchMapEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.mapper.EventInstanceMapper;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventImageRepository;
import com.example.openoff.domain.eventInstance.domain.repository.EventIndexRepository;
import com.example.openoff.domain.eventInstance.domain.repository.EventInfoRepository;
import com.example.openoff.domain.eventInstance.infrastructure.dto.EventIndexStatisticsDto;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventSearchUseCase {
    private final UserUtils userUtils;
    private final EventInfoRepository eventInfoRepository;
    private final EventIndexRepository eventIndexRepository;
    private final EventImageRepository eventImageRepository;
    private final EventBookmarkRepository eventBookmarkRepository;


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

    public PageResponse<MainTapEventInfoResponse> getMainTapListByFieldType(FieldType fieldType, Long eventInfoId, Pageable pageable) {
        User user = userUtils.getUser();
        PageResponse<MainTapEventInfoResponse> responseList = eventInfoRepository.findMainTapEventInfoByFieldType(fieldType, eventInfoId, pageable);
        List<Long> eventInfoIdList = responseList.getContent().stream().map(MainTapEventInfoResponse::getEventInfoId).collect(Collectors.toList());

        Map<Long, LocalDateTime> eventDateByEventInfoId = eventIndexRepository.findEventDateByEventInfoId(eventInfoIdList);
        Map<Long, String> mainImageUrlByEventInfoId = eventImageRepository.findMainImageUrlByEventInfoId(eventInfoIdList);
        List<Long> isBookmarkList = eventBookmarkRepository.findByEventInfoIdAndUserId(eventInfoIdList, user.getId());
        responseList.getContent().stream()
                .forEach(data -> {
                    data.setMainImageUrl(mainImageUrlByEventInfoId.get(data.getEventInfoId()));
                    data.setEventDate(eventDateByEventInfoId.get(data.getEventInfoId()));
                    isBookmarkList.stream().filter(tuple -> tuple.equals(data.getEventInfoId())).findFirst()
                            .ifPresentOrElse(tuple -> { data.setIsBookmarked(true); },
                                                () -> { data.setIsBookmarked(false); }
                        );
                });
        return responseList;
    }

    public PageResponse<MainTapEventInfoResponse> getMainTapListByVogue(Long eventInfoId, Integer count, Pageable pageable) {
        User user = userUtils.getUser();
        PageResponse<MainTapEventInfoResponse> responseList = eventInfoRepository.findMainTapEventInfoByVogue(eventInfoId, count, pageable);
        List<Long> eventInfoIdList = responseList.getContent().stream().map(MainTapEventInfoResponse::getEventInfoId).collect(Collectors.toList());

        Map<Long, LocalDateTime> eventDateByEventInfoId = eventIndexRepository.findEventDateByEventInfoId(eventInfoIdList);
        Map<Long, String> mainImageUrlByEventInfoId = eventImageRepository.findMainImageUrlByEventInfoId(eventInfoIdList);
        List<Long> isBookmarkList = eventBookmarkRepository.findByEventInfoIdAndUserId(eventInfoIdList, user.getId());
        responseList.getContent().stream()
                .forEach(data -> {
                    data.setMainImageUrl(mainImageUrlByEventInfoId.get(data.getEventInfoId()));
                    data.setEventDate(eventDateByEventInfoId.get(data.getEventInfoId()));
                    isBookmarkList.stream().filter(tuple -> tuple.equals(data.getEventInfoId())).findFirst()
                            .ifPresentOrElse(tuple -> { data.setIsBookmarked(true); },
                                    () -> { data.setIsBookmarked(false); }
                            );
                });
        return responseList;
    }
}

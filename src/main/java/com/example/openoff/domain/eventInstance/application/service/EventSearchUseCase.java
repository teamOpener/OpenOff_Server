package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.bookmark.domain.service.BookmarkService;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.DetailEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.EventIdListResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.HostEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.MainTapEventInfoResponse;
import com.example.openoff.domain.eventInstance.application.dto.response.SearchMapEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.SeoEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.mapper.EventInstanceMapper;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventImageService;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.interest.domain.entity.UserInterestField;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import com.example.openoff.domain.user.domain.entity.User;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventSearchUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventIndexService eventIndexService;
    private final EventImageService eventImageService;
    private final BookmarkService bookmarkService;
    private final EventApplicantLadgerService eventApplicantLadgerService;


    public List<SearchMapEventInfoResponseDto> searchMapEventInfo(EventSearchRequestDto eventSearchRequestDto)
    {
        List<EventInfo> eventMapList = eventInfoService.getEventMapList(eventSearchRequestDto);
        return EventInstanceMapper.mapToSearchMapEventInfoResponseList(eventMapList);
    }

    public SearchMapEventInfoResponseDto searchMapEventInfoOnlyOne(Long eventInfoId)
    {
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        return EventInstanceMapper.mapToSearchMapEventInfoResponse(eventInfo);
    }

    public DetailEventInfoResponseDto getDetailEventInfo(Long eventInfoId, Boolean isLogin){
        LocalDateTime now = LocalDateTime.now();
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        DetailEventInfoResponseDto detailEventInfoResponseDto = EventInstanceMapper.mapToDetailEventInfoResponse(eventInfo);
        detailEventInfoResponseDto.setIsEnded(eventInfo.getEventIndexes().stream().map(EventIndex::getEventDate).noneMatch(eventDate -> eventDate.isAfter(now)));
        Map<Long, Long> countEventInfoApprovedApplicant = eventApplicantLadgerService.countEventInfoApprovedApplicant(eventInfoId);

        if (isLogin) {
            User user = userUtils.getUser();
            detailEventInfoResponseDto.setIsBookmarked(bookmarkService.existsByEventInfo_IdAndUser_Id(eventInfoId, user.getId()));
            List<DetailEventInfoResponseDto.IndexInfo> indexInfoList = eventInfo.getEventIndexes().stream()
                    .sorted(Comparator.comparing(EventIndex::getEventDate))
                    .map(eventIndex -> DetailEventInfoResponseDto.IndexInfo.builder()
                            .eventIndexId(eventIndex.getId())
                            .eventDate(eventIndex.getEventDate())
                            .approvedUserCount(
                                    Math.toIntExact(countEventInfoApprovedApplicant.getOrDefault(eventIndex.getId(), 0L))
                            )
                            .isApply(eventIndex.getEventDate().isAfter(now) && !eventApplicantLadgerService.existsByEventIndex_IdAndEventApplicant_Id(eventIndex.getId(), user.getId()))
                            .build()
                    ).collect(Collectors.toList());
            detailEventInfoResponseDto.setIndexList(indexInfoList);
        } else {
            detailEventInfoResponseDto.setIsBookmarked(false);
            List<DetailEventInfoResponseDto.IndexInfo> indexInfoList = eventInfo.getEventIndexes().stream()
                    .sorted(Comparator.comparing(EventIndex::getEventDate))
                    .map(eventIndex -> DetailEventInfoResponseDto.IndexInfo.builder()
                            .eventIndexId(eventIndex.getId())
                            .eventDate(eventIndex.getEventDate())
                            .approvedUserCount(
                                    Math.toIntExact(countEventInfoApprovedApplicant.getOrDefault(eventIndex.getId(), 0L))
                            )
                            .isApply(eventIndex.getEventDate().isAfter(now))
                            .build()
                    ).collect(Collectors.toList());
            detailEventInfoResponseDto.setIndexList(indexInfoList);
        }
        return detailEventInfoResponseDto;
    }

    public List<MainTapEventInfoResponse> getPersonalEventInfoList(){
        User user = userUtils.getUser();
        List<FieldType> myInterests = user.getUserInterestFields().stream().map(UserInterestField::getFieldType).collect(Collectors.toList());
        List<EventInfo> eventInfoByMyInterestFields = eventInfoService.findEventInfoByMyInterestFields(myInterests);
        List<MainTapEventInfoResponse> responses = EventInstanceMapper.mapToMainTapEventInfoResponseList(eventInfoByMyInterestFields);
        responses.stream()
                .forEach(mainTapEventInfoResponse -> mainTapEventInfoResponse.setIsBookmarked(
                        bookmarkService.existsByEventInfo_IdAndUser_Id(mainTapEventInfoResponse.getEventInfoId(), user.getId())));
        return responses;
    }

    public PageResponse<MainTapEventInfoResponse> getMainTapList(Long eventInfoId, FieldType fieldType, Integer count, Pageable pageable) {
        User user = userUtils.getUser();
        if (fieldType != null) {
            Page<EventInfo> mainTapEventByField = eventInfoService.getMainTapEventByField(fieldType, eventInfoId, pageable);
            PageResponse<MainTapEventInfoResponse> response = EventInstanceMapper.mapToMainTapEventInfoResponse(mainTapEventByField);
            response.getContent().stream()
                    .forEach(mainTapEventInfoResponse -> mainTapEventInfoResponse.setIsBookmarked(
                            bookmarkService.existsByEventInfo_IdAndUser_Id(mainTapEventInfoResponse.getEventInfoId(), user.getId())));
            return response;
        } else {
            Page<EventInfo> mainTapEventByVogue = eventInfoService.getMainTapEventByVogue(eventInfoId, count, pageable);
            PageResponse<MainTapEventInfoResponse> response = EventInstanceMapper.mapToMainTapEventInfoResponse(mainTapEventByVogue);
            response.getContent().stream()
                    .forEach(mainTapEventInfoResponse -> mainTapEventInfoResponse.setIsBookmarked(
                            bookmarkService.existsByEventInfo_IdAndUser_Id(mainTapEventInfoResponse.getEventInfoId(), user.getId())));
            return response;
        }
    }

    public PageResponse<MainTapEventInfoResponse> getNotLoginMainTapList(Long eventInfoId, FieldType fieldType, Integer count, Pageable pageable) {
        if (fieldType != null) {
            Page<EventInfo> mainTapEventByField = eventInfoService.getMainTapEventByField(fieldType, eventInfoId, pageable);
            PageResponse<MainTapEventInfoResponse> response = EventInstanceMapper.mapToMainTapEventInfoResponse(mainTapEventByField);
            response.getContent()
                    .forEach(mainTapEventInfoResponse -> mainTapEventInfoResponse.setIsBookmarked(false));
            return response;
        } else {
            Page<EventInfo> mainTapEventByVogue = eventInfoService.getMainTapEventByVogue(eventInfoId, count, pageable);
            PageResponse<MainTapEventInfoResponse> response = EventInstanceMapper.mapToMainTapEventInfoResponse(mainTapEventByVogue);
            response.getContent()
                    .forEach(mainTapEventInfoResponse -> mainTapEventInfoResponse.setIsBookmarked(false));
            return response;
        }
    }

    public PageResponse<HostEventInfoResponseDto> getHostEventInfoList(Long eventInfoId, FieldType fieldType, Pageable pageable) {
        User user = userUtils.getUser();
        Page<EventInfo> hostEventList = eventInfoService.getHostEventList(user.getId(), eventInfoId, fieldType, pageable);
        return EventInstanceMapper.mapToHostEventInfoResponseList(hostEventList);
    }

    public EventIdListResponseDto getOpenEventIdsDto(){
        List<Long> openEventIdList = eventInfoService.getOpenEventIdList();
        return EventIdListResponseDto.builder().eventIdList(openEventIdList).build();
    }

    public SeoEventInfoResponseDto getSeoEventInfo(Long eventInfoId){
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        String mainImageUrl = eventImageService.getMainEventImageByEventInfoId(eventInfoId);
        List<LocalDateTime> dateList = eventIndexService.findEventIndexByEventInfoId(eventInfoId)
            .stream().map(EventIndex::getEventDate).collect(Collectors.toList())
            .stream().sorted().collect(Collectors.toList());
        return SeoEventInfoResponseDto.builder()
            .eventId(eventInfo.getId())
            .title(eventInfo.getEventTitle())
            .streetLoadAddress(eventInfo.getLocation().getStreetNameAddress())
            .detailAddress(eventInfo.getLocation().getDetailAddress())
            .imageUrl(mainImageUrl)
            .eventDateList(dateList)
            .build();
    }
}

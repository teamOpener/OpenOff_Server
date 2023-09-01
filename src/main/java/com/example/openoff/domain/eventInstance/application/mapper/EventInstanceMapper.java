package com.example.openoff.domain.eventInstance.application.mapper;

import com.example.openoff.common.annotation.Mapper;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.CommonUtils;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.*;
import com.example.openoff.domain.eventInstance.domain.entity.EventImage;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.EventInterestField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class EventInstanceMapper {

    public static CreateNewEventResponseDto mapToEventInstanceInfoResponse
            (
                    CreateNewEventRequestDto createDataInfo,
                    Long eventInfoId, List<Long> eventIndexIdList, List<Long> eventImageIdList,
                    List<Long> eventQuestionIdList, List<Long> eventInterestFieldIdList, List<Long> eventStaffIds
            )
    {
        return CreateNewEventResponseDto.builder()
                .createDataInfo(createDataInfo)
                .eventInfoId(eventInfoId)
                .eventIndexIdList(eventIndexIdList)
                .eventImageIdList(eventImageIdList)
                .eventQuestionIdList(eventQuestionIdList)
                .eventInterestFieldIdList(eventInterestFieldIdList)
                .eventStaffIds(eventStaffIds)
                .build();
    }

    public static SearchMapEventInfoResponseDto mapToSearchMapEventInfoResponse(EventInfo eventInfo){
        return SearchMapEventInfoResponseDto.builder()
                .id(eventInfo.getId())
                .title(eventInfo.getEventTitle())
                .fieldTypeList(eventInfo.getEventInterestFields().stream().map(EventInterestField::getFieldType).collect(Collectors.toList()))
                .streetLoadAddress(eventInfo.getLocation().getStreetNameAddress())
                .detailAddress(eventInfo.getLocation().getDetailAddress())
                .longitude(eventInfo.getLocation().getLongitude())
                .latitude(eventInfo.getLocation().getLatitude())
                .imageList(eventInfo.getEventImages().stream()
                        .map(eventImage -> SearchMapEventInfoResponseDto.ImageInfo.of(eventImage.getEventImageUrl(), eventImage.getIsMain()))
                        .collect(Collectors.toList()))
                .eventDateList(eventInfo.getEventIndexes().stream().map(EventIndex::getEventDate).collect(Collectors.toList()))
                .build();
    }

    public static List<SearchMapEventInfoResponseDto> mapToSearchMapEventInfoResponseList(List<EventInfo> eventInfoList){
        return eventInfoList.stream().map(EventInstanceMapper::mapToSearchMapEventInfoResponse).collect(Collectors.toList());
    }


    public static DetailEventInfoResponseDto mapToDetailEventInfoResponse(EventInfo eventInfo) {
        return DetailEventInfoResponseDto.builder()
                .eventId(eventInfo.getId())
                .title(eventInfo.getEventTitle())
                .streetLoadAddress(eventInfo.getLocation().getStreetNameAddress())
                .detailAddress(eventInfo.getLocation().getDetailAddress())
                .eventFee(eventInfo.getEventFee())
                .description(eventInfo.getEventDescription())
                .maxCapacity(eventInfo.getEventMaxPeople())
                .longitude(eventInfo.getLocation().getLongitude())
                .latitude(eventInfo.getLocation().getLatitude())
                .eventApplyStartDate(eventInfo.getEventApplyStartDate())
                .eventApplyEndDate(eventInfo.getEventApplyEndDate())
                .isApplyPermit(eventInfo.getEventApplyPermit())
                .imageList(eventInfo.getEventImages().stream()
                        .map(eventImage -> DetailEventInfoResponseDto.ImageInfo.of(eventImage.getEventImageUrl(), eventImage.getIsMain()))
                        .collect(Collectors.toList()))
                .extraQuestionList(eventInfo.getEventExtraQuestions().stream()
                        .map(eventQuestion -> DetailEventInfoResponseDto.ExtraQuestionInfo.of(eventQuestion.getId(), eventQuestion.getQuestion()))
                        .collect(Collectors.toList()))
                .build();
    }

    public static PageResponse<MainTapEventInfoResponse> mapToMainTapEventInfoResponse(Page<EventInfo> eventInfos) {
        List<MainTapEventInfoResponse> responses = eventInfos.stream().map(data -> {
            List<LocalDateTime> dateTimeList = data.getEventIndexes().stream().map(EventIndex::getEventDate).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList());
            return MainTapEventInfoResponse.builder()
                    .eventInfoId(data.getId())
                    .eventTitle(data.getEventTitle())
                    .streetRoadAddress(data.getLocation().getStreetNameAddress())
                    .totalApplicantCount(data.getTotalRegisterCount())
                    .fieldTypes(data.getEventInterestFields().stream().map(EventInterestField::getFieldType).collect(Collectors.toList()))
                    .eventDate(CommonUtils.formatLocalDateTimes(dateTimeList))
                    .mainImageUrl(data.getEventImages().stream()
                            .filter(image -> image.getIsMain().equals(true))
                            .map(EventImage::getEventImageUrl).findFirst().orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND)))
                    .build();
            }).collect(Collectors.toList());

        return PageResponse.of(new PageImpl<>(responses, eventInfos.getPageable(), eventInfos.getTotalElements()));
    }

    public static PageResponse<HostEventInfoResponseDto> mapToHostEventInfoResponseList(Page<EventInfo> eventInfoList) {
        List<HostEventInfoResponseDto> responseDtos = eventInfoList.stream().map(data ->
                HostEventInfoResponseDto.builder()
                    .eventInfoId(data.getId())
                    .eventTitle(data.getEventTitle())
                    .isApproved(data.getIsApproval())
                    .eventPeriod(CommonUtils.formatLocalDateTimes(data.getEventIndexes().stream().map(EventIndex::getEventDate).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList())))
                    .eventIndexInfoList(data.getEventIndexes().stream()
                            .map(eventIndex -> HostEventInfoResponseDto.EventIndexInfo.of(eventIndex.getId(), eventIndex.getEventDate())).collect(Collectors.toList()))
                    .fieldTypeList(data.getEventInterestFields().stream().map(EventInterestField::getFieldType).collect(Collectors.toList()))
                    .build()).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, eventInfoList.getPageable(), eventInfoList.getTotalElements()));
    }

    public static List<MainTapEventInfoResponse> mapToMainTapEventInfoResponseList(List<EventInfo> eventInfoList){
        return eventInfoList.stream().map(data ->
                        MainTapEventInfoResponse.builder()
                                .eventInfoId(data.getId())
                                .eventTitle(data.getEventTitle())
                                .streetRoadAddress(data.getLocation().getStreetNameAddress())
                                .totalApplicantCount(data.getTotalRegisterCount())
                                .fieldTypes(data.getEventInterestFields().stream().map(EventInterestField::getFieldType).collect(Collectors.toList()))
                                .eventDate(CommonUtils.formatLocalDateTimes(data.getEventIndexes().stream().map(EventIndex::getEventDate).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList())))
                                .mainImageUrl(data.getEventImages().stream()
                                        .filter(image -> image.getIsMain().equals(true))
                                        .map(EventImage::getEventImageUrl).findFirst().orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND)))
                                .build()
                ).collect(Collectors.toList());
    }
}

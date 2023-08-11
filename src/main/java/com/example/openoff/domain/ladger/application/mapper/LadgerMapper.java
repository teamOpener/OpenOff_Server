package com.example.openoff.domain.ladger.application.mapper;

import com.example.openoff.common.annotation.Mapper;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.interest.domain.entity.EventInterestField;
import com.example.openoff.domain.ladger.application.dto.response.*;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Mapper
public class LadgerMapper {
    public static PageResponse<ApplicationInfoResponseDto> mapToApplicationInfoResponseDto(Page<EventInfo> ladgerInfoList, List<EventApplicantLadger> eventApplicantLadgers) {
        List<ApplicationInfoResponseDto> dtos = ladgerInfoList.stream().map(data ->
                ApplicationInfoResponseDto.builder()
                        .eventInfoId(data.getId())
                        .eventTitle(data.getEventTitle())
                        .eventDateList(data.getEventIndexes().stream().map(EventIndex::getEventDate).collect(Collectors.toList()))
                        .fieldTypeList(data.getEventInterestFields().stream().map(EventInterestField::getFieldType).collect(Collectors.toList()))
                        .eventApplicantLadgerInfoList(eventApplicantLadgers.stream()
                                .filter(ladger -> ladger.getEventInfo().getId().equals(data.getId()))
                                .map(ladger -> ApplicationInfoResponseDto.EventApplicantLadgerInfo.of(ladger.getId(), ladger.getTicketIndex())).collect(Collectors.toList()))
                        .build()
        ).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(dtos, ladgerInfoList.getPageable(), ladgerInfoList.getTotalElements()));
    }

    public static List<MyTicketInfoResponseDto> mapToMyTicketInfoResponseDtoList(User user, List<EventApplicantLadger> ladgerInfoList) {
        return ladgerInfoList.stream().map(data -> MyTicketInfoResponseDto.builder()
                .username(user.getUserName())
                .birth(user.getBirth().getYear().toString() + "." + user.getBirth().getMonth() + "." + user.getBirth().getDay())
                .eventInfoId(data.getEventInfo().getId())
                .eventIndexId(data.getEventIndex().getId())
                .eventTitle(data.getEventInfo().getEventTitle())
                .streetRoadAddress(data.getEventInfo().getLocation().getStreetNameAddress())
                .ticketIndex(data.getTicketIndex())
                .ticketType(data.getTicketType())
                .eventDate(data.getEventIndex().getEventDate())
                .isAccepted(data.getIsAccept())
                .isJoined(data.getIsJoin())
                .qrImageUrl(data.getQrCodeImageUrl())
                .build()).collect(Collectors.toList());
    }

    public static PageResponse<EventApplicantInfoResponseDto> mapToEventApplicantInfoResponseDto(Page<EventApplicantLadger> ladgerInfoList) {
        List<EventApplicantInfoResponseDto> responseDtos = ladgerInfoList.get().map(data -> EventApplicantInfoResponseDto.builder()
                .userId(data.getEventApplicant().getId())
                .username(data.getEventApplicant().getUserName())
                .birth(data.getEventApplicant().getBirth().getYear().toString() + "." + data.getEventApplicant().getBirth().getMonth() + "." + data.getEventApplicant().getBirth().getDay())
                .genderType(data.getEventApplicant().getGender())
                .ladgerId(data.getId())
                .isAccepted(data.getIsAccept())
                .isJoined(data.getIsJoin())
                .createdAt(data.getCreatedDate()).build()).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, ladgerInfoList.getPageable(), ladgerInfoList.getTotalElements()));
    }

    public static ApplicantApplyDetailResponseDto mapToApplicantApplyDetailResponseDto(User user, EventApplicantLadger ladgerInfo) {
        return ApplicantApplyDetailResponseDto.builder()
                .username(user.getUserName())
                .birth(user.getBirth().getYear().toString() + "." + user.getBirth().getMonth() + "." + user.getBirth().getDay())
                .eventInfoId(ladgerInfo.getEventInfo().getId())
                .eventIndexId(ladgerInfo.getEventIndex().getId())
                .eventTitle(ladgerInfo.getEventInfo().getEventTitle())
                .streetRoadAddress(ladgerInfo.getEventInfo().getLocation().getStreetNameAddress())
                .eventDate(ladgerInfo.getEventIndex().getEventDate())
                .isAccepted(ladgerInfo.getIsAccept())
                .qnAInfoList(user.getEventExtraAnswerList().stream()
                        .filter(eventExtraAnswer -> eventExtraAnswer.getEventIndex().getId().equals(ladgerInfo.getEventIndex().getId()))
                        .map(eventExtraAnswer -> ApplicantApplyDetailResponseDto.QnAInfo.of(eventExtraAnswer.getQuestion().getQuestion(), eventExtraAnswer.getAnswer())).collect(Collectors.toList()))
                .build();
    }

    public static EventLadgerTotalStatusResponseDto mapToEventLadgerTotalStatusResponseDto(EventIndex eventIndex, List<EventApplicantLadger> ladgerList){
        return EventLadgerTotalStatusResponseDto.builder()
                .eventIndexId(eventIndex.getId())
                .eventDate(eventIndex.getEventDate())
                .isClosed(eventIndex.getIsClose())
                .maxCount(eventIndex.getEventInfo().getEventMaxPeople())
                .notApprovedCount(ladgerList.stream().filter(ladger -> ladger.getIsAccept().equals(false)).count())
                .approvedCount(ladgerList.stream().filter(ladger -> ladger.getIsJoin().equals(false) && ladger.getIsAccept().equals(true)).count())
                .joinedCount(ladgerList.stream().filter(ladger -> ladger.getIsJoin().equals(true)).count())
                .build();
    }
}

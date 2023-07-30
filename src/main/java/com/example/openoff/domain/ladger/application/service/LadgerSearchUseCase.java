package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.ladger.application.dto.response.ApplicantApplyDetailResponseDto;
import com.example.openoff.domain.ladger.application.dto.response.ApplicationInfoResponseDto;
import com.example.openoff.domain.ladger.application.dto.response.EventApplicantInfoResponseDto;
import com.example.openoff.domain.ladger.application.dto.response.MyTicketInfoResponseDto;
import com.example.openoff.domain.ladger.application.mapper.LadgerMapper;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import com.example.openoff.domain.ladger.presentation.SortType;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LadgerSearchUseCase {
    private final UserUtils userUtils;
    private final EventIndexService eventIndexService;
    private final EventApplicantLadgerService eventApplicantLadgerService;

    public PageResponse<ApplicationInfoResponseDto> getMyLadgerInfos(Long eventInfoId, FieldType fieldType, Pageable pageable) {
        User user = userUtils.getUser();
        Page<EventInfo> ladgerEventInfoList = eventApplicantLadgerService.findAllByEventIndexId(eventInfoId, fieldType, user.getId(), pageable);
        List<Long> eventInfoIds = ladgerEventInfoList.stream().map(EventInfo::getId).collect(Collectors.toList());
        List<EventApplicantLadger> ladgerInfoList = eventApplicantLadgerService.findMyTotalEventTicketInfos(eventInfoIds, user.getId());

//        List<EventApplicantLadger> ladgerInfos = eventApplicantLadgerService.findAllByEventIndexId(eventInfoId, fieldType, user.getId());
        return LadgerMapper.mapToApplicationInfoResponseDto(ladgerEventInfoList, ladgerInfoList);
    }

    public List<MyTicketInfoResponseDto> getApplyEventTicketInfos(Long eventInfoId) {
        User user = userUtils.getUser();
        List<EventApplicantLadger> ladgerInfoList = eventApplicantLadgerService.findMySpecificEventTicketInfos(eventInfoId, user.getId());
        return LadgerMapper.mapToMyTicketInfoResponseDtoList(user, ladgerInfoList);
    }

    public ApplicantApplyDetailResponseDto getApplyEventTicketInfo(Long ladgerId) {
        User user = userUtils.getUser();
        EventApplicantLadger ladgerInfo = eventApplicantLadgerService.findMyEventTicketInfo(ladgerId, user.getId());
        return LadgerMapper.mapToMyTicketInfoResponseDto(user, ladgerInfo);
    }

    public PageResponse<EventApplicantInfoResponseDto> getEventApplicantList(Long eventIndexId, String username, LocalDateTime time, String keyword, SortType sort, Pageable pageable) {
        User user = userUtils.getUser();
        if (eventIndexService.findById(eventIndexId).getEventInfo().getEventStaffs().stream().noneMatch(staff -> staff.getStaff().getId().equals(user.getId()))) {
            throw BusinessException.of(Error.EVENT_STAFF_NOT_FOUND);
        }
        Page<EventApplicantLadger> ladgerInfoList = eventApplicantLadgerService.findAllEventApplicants(eventIndexId, username, time, keyword, sort, pageable);
        return LadgerMapper.mapToEventApplicantInfoResponseDto(ladgerInfoList);
    }
}

package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.ladger.application.dto.response.*;
import com.example.openoff.domain.ladger.application.service.LadgerSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/ladger")
@RequiredArgsConstructor
public class LadgerGetController {
    private final LadgerSearchUseCase ladgerSearchUseCase;

    @GetMapping(value = "/tickets")
    public ResponseEntity<ResponseDto<PageResponse<ApplicationInfoResponseDto>>> getMyAllApplyInfos
            (
                    @RequestParam(required = false) FieldType fieldType,
                    @RequestParam(required = false) Long eventInfoId,
                    @PageableDefault(size = 1) Pageable pageable
            )
    {
        PageResponse<ApplicationInfoResponseDto> myLadgerInfos = ladgerSearchUseCase.getMyLadgerInfos(eventInfoId, fieldType, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청 목록 조회가 완료되었습니다.", myLadgerInfos));
    }

    @GetMapping(value = "/tickets/{eventInfoId}")
    public ResponseEntity<ResponseDto<List<MyTicketInfoResponseDto>>> getEventTicketInfos(@PathVariable Long eventInfoId)
    {
        List<MyTicketInfoResponseDto> myTicketInfoResponseDtoList = ladgerSearchUseCase.getApplyEventTicketInfos(eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "해당 이벤트 티켓 목록 조회가 완료되었습니다.", myTicketInfoResponseDtoList));
    }

    @GetMapping(value = "/ticket/{ladgerId}")
    public ResponseEntity<ResponseDto<ApplicantApplyDetailResponseDto>> getEventTicketInfo(@PathVariable Long ladgerId)
    {
        ApplicantApplyDetailResponseDto applicantApplyDetailResponseDto = ladgerSearchUseCase.getApplyEventTicketInfo(ladgerId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "해당 이벤트 티켓 조회가 완료되었습니다.", applicantApplyDetailResponseDto));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ResponseDto<PageResponse<EventApplicantInfoResponseDto>>> getApplicantInfos
            (
                    @RequestParam Long eventIndexId,
                    @RequestParam(required = false) String username,
                    @RequestParam(required = false) String time,
                    @RequestParam(required = false) String keyword,
                    @RequestParam SortType sort,
                    @PageableDefault(size = 1) Pageable pageable
            )
    {
        DateTimeFormatter parse = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        LocalDateTime convertedTime = null;
        if (time != null) { convertedTime = LocalDateTime.parse(time, parse); }
        PageResponse<EventApplicantInfoResponseDto> eventApplicantInfoResponseDtoPageResponse = ladgerSearchUseCase.getEventApplicantList(eventIndexId, username, convertedTime, keyword, sort, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청자 목록 조회가 완료되었습니다.", eventApplicantInfoResponseDtoPageResponse));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<ResponseDto<EventLadgerTotalStatusResponseDto>> getEventIndexStatus
            (
                    @RequestParam Long eventIndexId
            )
    {
        EventLadgerTotalStatusResponseDto eventIndexLadgerStatus = ladgerSearchUseCase.getEventIndexLadgerStatus(eventIndexId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청자 현황 조회가 완료되었습니다.", eventIndexLadgerStatus));
    }

}

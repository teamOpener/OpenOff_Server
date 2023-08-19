package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.ladger.application.dto.response.EventStaffInfoResponseDto;
import com.example.openoff.domain.ladger.application.service.StaffSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/staff")
@RequiredArgsConstructor
public class StaffLadgerGetController {
    private final StaffSearchUseCase staffSearchUseCase;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseDto<List<EventStaffInfoResponseDto>>> getEventStaffList
            (
                    @RequestParam Long eventInfoId
            )
    {
        List<EventStaffInfoResponseDto> eventStaffList = staffSearchUseCase.getEventStaffList(eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 스태프 목록 조회가 완료되었습니다.", eventStaffList));
    }

}

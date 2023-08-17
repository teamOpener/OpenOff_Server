package com.example.openoff.domain.eventInstance.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.eventInstance.application.service.EventUpdateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/event-instance")
@RequiredArgsConstructor
public class EventInstancePatchController {
    private final EventUpdateUseCase eventUpdateUseCase;

    @PatchMapping(value = "/suspension")
    public ResponseEntity<ResponseDto<Void>> suspensionEventApply(@RequestParam Long eventInfoId)
    {
        eventUpdateUseCase.suspensionEventApplication(eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청 중단이 완료되었습니다.", null));
    }
}

package com.example.openoff.domain.eventInstance.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.eventInstance.application.service.EventDeleteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/event-instance")
@RequiredArgsConstructor
public class EventInstanceDeleteController {
    private final EventDeleteUseCase eventDeleteUseCase;

    @DeleteMapping(value = "/remove/{eventInfoId}")
    public ResponseEntity<ResponseDto<Void>> deleteEvent(@PathVariable Long eventInfoId) {
        eventDeleteUseCase.deleteEvent(eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 삭제가 완료되었습니다.", null));
    }
}

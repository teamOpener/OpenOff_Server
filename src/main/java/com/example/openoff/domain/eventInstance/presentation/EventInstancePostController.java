package com.example.openoff.domain.eventInstance.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.CreateNewEventResponseDto;
import com.example.openoff.domain.eventInstance.application.service.EventCreateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/event-instance")
@RequiredArgsConstructor
public class EventInstancePostController {
    private final EventCreateUseCase eventCreateUseCase;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto<CreateNewEventResponseDto>> createNewEvent(@RequestBody CreateNewEventRequestDto createNewEventRequestDto) {
        CreateNewEventResponseDto newEventResponseDto = eventCreateUseCase.createEvent(createNewEventRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "새로운 이벤트 개설 신청이 완료되었습니다.", newEventResponseDto));
    }
}

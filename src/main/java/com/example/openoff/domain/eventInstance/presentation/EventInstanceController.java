package com.example.openoff.domain.eventInstance.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.CreateNewEventResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.SearchMapEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.service.EventCreateUseCase;
import com.example.openoff.domain.eventInstance.application.service.EventSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/event-instance")
@RequiredArgsConstructor
public class EventInstanceController {
    private final EventCreateUseCase eventCreateUseCase;
    private final EventSearchUseCase eventSearchUseCase;

    @GetMapping(value = "/search")
    public ResponseEntity<ResponseDto<List<SearchMapEventInfoResponseDto>>> searchMapEventInfo
            (@ModelAttribute EventSearchRequestDto eventSearchRequestDto)
    {
        log.info("eventSearchRequestDto: {}", eventSearchRequestDto.toString());
        List<SearchMapEventInfoResponseDto> searchMapEventInfoResponseDtoList = eventSearchUseCase.searchMapEventInfo(eventSearchRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "검색 조건에 따라 지도에 이벤트 정보를 불러오는데 성공하였습니다.", searchMapEventInfoResponseDtoList));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto<CreateNewEventResponseDto>> createNewEvent(@RequestBody CreateNewEventRequestDto createNewEventRequestDto) {
        CreateNewEventResponseDto newEventResponseDto = eventCreateUseCase.createEvent(createNewEventRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "새로운 이벤트 개설 신청이 완료되었습니다.", newEventResponseDto));
    }
}

package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.ladger.application.dto.request.ApplyEventRequestDto;
import com.example.openoff.domain.ladger.application.service.LadgerCreateUseCase;
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
@RequestMapping(value = "/ladger")
@RequiredArgsConstructor
public class LadgerPostController {
    private final LadgerCreateUseCase ladgerCreateUseCase;

    @PostMapping(value = "/apply")
    public ResponseEntity<ResponseDto<Void>> applyEvent(@RequestBody ApplyEventRequestDto applyEventRequestDto)
    {
        ladgerCreateUseCase.createEventApplicationLadger(applyEventRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청이 완료되었습니다.", null));
    }
}

package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.ladger.application.dto.request.EventStaffCreateRequestDto;
import com.example.openoff.domain.ladger.application.service.StaffCreateUseCase;
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
@RequestMapping(value = "/staff")
@RequiredArgsConstructor
public class StaffLadgerPostController {
    private final StaffCreateUseCase staffCreateUseCase;

    @PostMapping(value = "/plus")
    public ResponseEntity<ResponseDto<Void>> addEventStaff
            (
                    @RequestBody EventStaffCreateRequestDto eventStaffCreateRequestDto
            )
    {
        staffCreateUseCase.addNewSubStaff(eventStaffCreateRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 스태프 추가가 완료되었습니다.", null));
    }

}

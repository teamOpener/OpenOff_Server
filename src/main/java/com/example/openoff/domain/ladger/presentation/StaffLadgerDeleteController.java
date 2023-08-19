package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.ladger.application.service.StaffDeleteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/staff")
@RequiredArgsConstructor
public class StaffLadgerDeleteController {
    private final StaffDeleteUseCase staffDeleteUseCase;

    @DeleteMapping(value = "/minus")
    public ResponseEntity<ResponseDto<Void>> removeEventStaff
            (
                    @RequestParam String staffName, @RequestParam Long eventInfoId
            )
    {
        staffDeleteUseCase.deleteEventStaff(staffName, eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 스태프 삭제가 완료되었습니다.", null));
    }

}

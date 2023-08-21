package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.ladger.application.service.LadgerDeleteUseCase;
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
@RequestMapping(value = "/ladger")
@RequiredArgsConstructor
public class LadgerDeleteController {
    private final LadgerDeleteUseCase ladgerDeleteUseCase;

    @DeleteMapping(value = "/own/cancel/{ladgerId}")
    public ResponseEntity<ResponseDto<Void>> deleteMyApplyLadger(@PathVariable Long ladgerId)
    {
        ladgerDeleteUseCase.deleteMyApplyLadger(ladgerId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청이 취소되었습니다.", null));
    }

    @DeleteMapping(value = "/reject/{ladgerId}")
    public ResponseEntity<ResponseDto<Void>> deleteRejectLadger(@PathVariable Long ladgerId, @PathVariable String rejectReason)
    {
        ladgerDeleteUseCase.deleteRejectLadger(ladgerId, rejectReason);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청이 거부되었습니다.", null));
    }
}

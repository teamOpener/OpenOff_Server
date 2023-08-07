package com.example.openoff.domain.ladger.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.ladger.application.dto.request.QRCheckRequestDto;
import com.example.openoff.domain.ladger.application.dto.response.QRCheckResponseDto;
import com.example.openoff.domain.ladger.application.service.LadgerUpdateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/ladger")
@RequiredArgsConstructor
public class LadgerPatchController {
    private final LadgerUpdateUseCase ladgerUpdateUseCase;

    @PatchMapping(value = "/permit")
    public ResponseEntity<ResponseDto<Void>> applyEvent(@RequestParam Long ladgerId)
    {
        ladgerUpdateUseCase.permitAndUpdateQRImageUrl(ladgerId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청 승인이 완료되었습니다.", null));
    }

    @PatchMapping(value = "/permit/all")
    public ResponseEntity<ResponseDto<Void>> applyEventAll(@RequestParam Long eventIndexId)
    {
        ladgerUpdateUseCase.permitAndUpdateQRImageUrlAllApplicant(eventIndexId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청 승인이 완료되었습니다.", null));
    }

    @PatchMapping(value = "/permit/cancel")
    public ResponseEntity<ResponseDto<Void>> cancelAcceptedEvent(@RequestParam Long ladgerId)
    {
        ladgerUpdateUseCase.cancelPermitedApplicantion(ladgerId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 신청 승인 취소되었습니다.", null));
    }

    @PatchMapping(value = "/qr/check")
    public ResponseEntity<ResponseDto<QRCheckResponseDto>> applyEvent(@RequestBody QRCheckRequestDto qrCheckRequestDto)
    {
        QRCheckResponseDto qrCheckResponseDto = ladgerUpdateUseCase.checkQRCode(qrCheckRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 참석이 완료되었습니다.", qrCheckResponseDto));
    }
}

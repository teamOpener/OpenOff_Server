package com.example.openoff.domain.notification.presentation;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.notification.application.service.NotificationUpdateUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/notification")
@RequiredArgsConstructor
public class NotificationPatchController {
    private final NotificationUpdateUseCase notificationUpdateUseCase;

    @PatchMapping(value = "/check")
    public ResponseEntity<ResponseDto<Void>> readNotification(@RequestParam Long notificationId)
    {
        notificationUpdateUseCase.readNotification(notificationId);
        return ResponseEntity.ok(ResponseDto.of(200, "알림 읽기가 완료되었습니다.", null));
    }
}

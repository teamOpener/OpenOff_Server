package com.example.openoff.domain.notification.presentation;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.notification.application.dto.response.NotificationInfoResponseDto;
import com.example.openoff.domain.notification.application.service.NotificationSearchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/notification")
@RequiredArgsConstructor
public class NotificationGetController {
    private final NotificationSearchUseCase notificationSearchUseCase;

    @GetMapping(value = "/list")
    public ResponseEntity<ResponseDto<PageResponse<NotificationInfoResponseDto>>> getNotificationList
            (
                    @RequestParam(required = false) Long notificationId,
                    @PageableDefault(size = 8) Pageable pageable
            )
    {
        PageResponse<NotificationInfoResponseDto> notificationList = notificationSearchUseCase.getNotificationList(notificationId, pageable);
        return ResponseEntity.ok(ResponseDto.of(200, "알림 목록 조회가 완료되었습니다.", notificationList));
    }
}

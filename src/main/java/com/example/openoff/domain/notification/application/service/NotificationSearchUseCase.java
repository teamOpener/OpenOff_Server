package com.example.openoff.domain.notification.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.notification.application.dto.response.NotificationInfoResponseDto;
import com.example.openoff.domain.notification.application.mapper.NotificationMapper;
import com.example.openoff.domain.notification.domain.entity.Notification;
import com.example.openoff.domain.notification.domain.service.NotificationService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationSearchUseCase {
    private final UserUtils userUtils;
    private final NotificationService notificationService;


    public PageResponse<NotificationInfoResponseDto> getNotificationList(Long notificationId, Pageable pageable) {
        User user = userUtils.getUser();
        Page<Notification> notificationList = notificationService.getNotificationList(notificationId, user.getId(), pageable);
        return NotificationMapper.mapToNotificationInfoResponseDtoPageResponse(notificationList);
    }
}

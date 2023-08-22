package com.example.openoff.domain.notification.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.notification.domain.service.NotificationService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class NotificationUpdateUseCase {
    private final UserUtils userUtils;
    private final NotificationService notificationService;


    public void readNotification(Long notificationId) {
        User user = userUtils.getUser();
        notificationService.updateNotificationIsRead(notificationId, user.getId());
    }
}

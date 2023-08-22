package com.example.openoff.domain.notification.domain.repository;

import com.example.openoff.domain.notification.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationRepositoryCustom {
    Boolean existsNotificationInSpecificCase(String userId, String content, Long notificationParameter);
    Page<Notification> getNotificationList(Long notificationId, String userId, Pageable pageable);
}

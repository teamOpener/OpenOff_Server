package com.example.openoff.domain.notification.domain.repository;

public interface NotificationRepositoryCustom {
    Boolean existsNotificationInSpecificCase(String userId, String content, Long notificationParameter);

}

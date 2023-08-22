package com.example.openoff.domain.notification.application.dto.response;

import com.example.openoff.domain.notification.domain.entity.NotificationType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationInfoResponseDto {
    private Long notificationId;
    private String content;
    private Boolean isRead;
    private NotificationType notificationType;
    private Long notificationParameter;
    private String createdAt;

    @Builder
    public NotificationInfoResponseDto(Long notificationId, String content, Boolean isRead, NotificationType notificationType, Long notificationParameter, String createdAt) {
        this.notificationId = notificationId;
        this.content = content;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.notificationParameter = notificationParameter;
        this.createdAt = createdAt;
    }
}

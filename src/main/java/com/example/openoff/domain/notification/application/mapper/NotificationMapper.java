package com.example.openoff.domain.notification.application.mapper;

import com.example.openoff.common.annotation.Mapper;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.domain.notification.application.dto.response.NotificationInfoResponseDto;
import com.example.openoff.domain.notification.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class NotificationMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M '월' d '일'");

    public static PageResponse<NotificationInfoResponseDto> mapToNotificationInfoResponseDtoPageResponse(Page<Notification> notificationList) {
        List<NotificationInfoResponseDto> responseDtos = notificationList.getContent().stream()
                .map(notification -> NotificationInfoResponseDto.builder()
                        .notificationId(notification.getId())
                        .content(notification.getContent())
                        .isRead(notification.getIsRead())
                        .notificationType(notification.getNotificationType())
                        .notificationParameter(notification.getNotificationParameter())
                        .createdAt(notification.getCreatedDate().format(formatter))
                        .build()).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, notificationList.getPageable(), notificationList.getTotalElements()));
    }
}

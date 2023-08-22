package com.example.openoff.domain.notification.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.notification.domain.entity.Notification;
import com.example.openoff.domain.notification.domain.entity.NotificationType;
import com.example.openoff.domain.notification.domain.repository.NotificationRepository;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void save(User user, String content, NotificationType notificationType, Long notificationParameter) {
        notificationRepository.save(Notification.toEntity(user, content, notificationType, notificationParameter));
    }

    public void saveBulk(List<User> users, String content, NotificationType notificationType, Long notificationParameter) {
        List<Notification> notifications = users.stream()
                .map(user -> Notification.toEntity(user, content, notificationType, notificationParameter))
                .collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
    }

    public boolean existsNotificationInSpecificCase(User user, String content, Long notificationParameter) {
        return notificationRepository.existsNotificationInSpecificCase(user.getId(), content, notificationParameter);
    }

    public void saveBulkAfterCheck(List<User> users, String content, NotificationType notificationType, Long notificationParameter) {
        List<Notification> notifications = users.stream()
                .filter(user -> !existsNotificationInSpecificCase(user, content, notificationParameter))
                .map(user -> Notification.toEntity(user, content, notificationType, notificationParameter))
                .collect(Collectors.toList());
        notificationRepository.saveAll(notifications);
    }

    public Page<Notification> getNotificationList(Long notificationId, String userId, Pageable pageable) {
        return notificationRepository.getNotificationList(notificationId, userId, pageable);
    }

    public void updateNotificationIsRead(Long notificationId, String userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
        if (notification.getUser().getId().equals(userId)) {
            notification.read();
            notificationRepository.save(notification);
        } else {
            throw BusinessException.of(Error.DATA_NOT_FOUND); // TODO: 에러코드 변경
        }
    }
}

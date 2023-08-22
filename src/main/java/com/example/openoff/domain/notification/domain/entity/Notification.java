package com.example.openoff.domain.notification.domain.entity;

import com.example.openoff.common.infrastructure.domain.BaseEntity;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "openoff_notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "notification_type", nullable = false)
    private NotificationType notificationType;

    @Column(name = "notification_parameter", nullable = false)
    private Long notificationParameter;

    @Builder
    public Notification(User user, String content, Boolean isRead, NotificationType notificationType, Long notificationParameter) {
        this.user = user;
        this.content = content;
        this.isRead = isRead;
        this.notificationType = notificationType;
        this.notificationParameter = notificationParameter;
    }


    public static Notification toEntity(User user, String content, NotificationType notificationType, Long notificationParameter) {
        return Notification.builder()
                .user(user)
                .content(content)
                .isRead(false)
                .notificationType(notificationType)
                .notificationParameter(notificationParameter)
                .build();
    }

    public void read() {
        this.isRead = true;
    }
}

package com.example.openoff.domain.notification.infrastructure;

import com.example.openoff.domain.notification.domain.repository.NotificationRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.openoff.domain.notification.domain.entity.QNotification.notification;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsNotificationInSpecificCase(String userId, String content, Long notificationParameter) {
        return queryFactory
                .selectOne()
                .from(notification)
                .where(
                        notification.user.id.eq(userId)
                                .and(notification.content.eq(content))
                                .and(notification.notificationParameter.eq(notificationParameter))
                )
                .fetchFirst() != null;
    }
}

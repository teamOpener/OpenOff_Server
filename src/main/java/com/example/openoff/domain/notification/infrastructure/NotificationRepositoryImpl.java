package com.example.openoff.domain.notification.infrastructure;

import com.example.openoff.domain.notification.domain.entity.Notification;
import com.example.openoff.domain.notification.domain.repository.NotificationRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Page<Notification> getNotificationList(Long notificationId, String userId, Pageable pageable) {
        List<Notification> notifications = queryFactory
                .select(notification)
                .from(notification)
                .where(
                        notification.user.id.eq(userId),
                        ltNotificationId(notificationId)
                )
                .orderBy(notification.id.desc())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(notification.count())
                .from(notification)
                .where(
                        notification.user.id.eq(userId),
                        ltNotificationId(notificationId)
                );

        return PageableExecutionUtils.getPage(notifications, pageable, countQuery::fetchOne);
    }

    private BooleanExpression ltNotificationId(Long notificationId) {
        return notificationId != null ? notification.id.lt(notificationId) : null;
    }
}

package com.example.openoff.domain.notification.application.handler;

import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.bookmark.domain.service.BookmarkService;
import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.notification.application.service.NotificationCreateUseCase;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class NotificationHandler {
    private final BookmarkService bookmarkService;
    private final EventStaffService eventStaffService;
    private final FirebaseService firebaseService;
    private final NotificationCreateUseCase notificationCreateUseCase;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eventInfoApplyPermitHandler(ApplyHalfEvent applyHalfEvent) {
        Long eventInfoId = applyHalfEvent.getEventIndex().getEventInfo().getId();

        List<User> bookmarkers = getBookmarkersByEventInfoId(eventInfoId);
        List<User> staffs = getStaffsByEventInfoId(eventInfoId);

        sendNotifications(bookmarkers, staffs, applyHalfEvent);

        unsubscribeUsersFromFirebase(bookmarkers, eventInfoId + "-bookmark-half");
        unsubscribeUsersFromFirebase(staffs, applyHalfEvent.getEventIndex().getId() + "-apply-half-staff-alert");
    }

    private List<User> getBookmarkersByEventInfoId(Long eventInfoId) {
        return bookmarkService.findBookmarkByEventInfoId(eventInfoId).stream()
                .map(EventBookmark::getUser).collect(Collectors.toList());
    }

    private List<User> getStaffsByEventInfoId(Long eventInfoId) {
        return eventStaffService.getEventStaffs(eventInfoId).stream()
                .map(EventStaff::getStaff).collect(Collectors.toList());
    }

    private void sendNotifications(List<User> bookmarkers, List<User> staffs, ApplyHalfEvent applyHalfEvent) {
        notificationCreateUseCase.createApplyHalfStaffNotification(staffs, applyHalfEvent.getEventIndex().getId());
        notificationCreateUseCase.createBookmarkHalfNotification(bookmarkers, applyHalfEvent.getEventIndex().getEventInfo().getId());
    }

    private void unsubscribeUsersFromFirebase(List<User> users, String topic) {
        List<String> fcmTokens = users.stream()
                .map(User::getUserFcmTokens)
                .flatMap(List::stream)
                .map(UserFcmToken::getFcmToken)
                .collect(Collectors.toList());

        if (!fcmTokens.isEmpty()) {
            firebaseService.unSubscribe(topic, fcmTokens);
        }
    }

}

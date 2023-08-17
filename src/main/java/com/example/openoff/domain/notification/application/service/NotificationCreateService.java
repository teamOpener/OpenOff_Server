package com.example.openoff.domain.notification.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.notification.domain.entity.NotificationType;
import com.example.openoff.domain.notification.domain.service.NotificationService;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class NotificationCreateService {
    private final NotificationService notificationService;
    private final FirebaseService firebaseService;


    public void createApplyPermitNotification(EventApplicantLadger eventApplicantLadger) {
        User eventApplicant = eventApplicantLadger.getEventApplicant();
        firebaseService.sendFCMNotificationMulticast(eventApplicant.getUserFcmTokens(), "이벤트 신청 완료", "이벤트 신청 승인이 완료되었습니다.\n생성된 QR 티켓을 확인해보세요!");
        notificationService.save(eventApplicant, "이벤트 신청 승인이 완료되었습니다.\n생성된 QR 티켓을 확인해보세요!", NotificationType.E, eventApplicantLadger.getEventInfo().getId());
    }

    public void createApplyPermitNotifications(List<EventApplicantLadger> eventApplicantLadgers) {
        List<User> users = eventApplicantLadgers.stream()
                .map(EventApplicantLadger::getEventApplicant).collect(Collectors.toList());
        List<UserFcmToken> userFcmTokens = users.stream()
                .map(User::getUserFcmTokens).flatMap(List::stream).collect(Collectors.toList());
        firebaseService.sendFCMNotificationMulticast(userFcmTokens, "이벤트 신청 완료", "이벤트 신청 승인이 완료되었습니다.\n생성된 QR 티켓을 확인해보세요!");
        notificationService.saveBulk(users, "이벤트 신청 승인이 완료되었습니다.\n생성된 QR 티켓을 확인해보세요!", NotificationType.E, eventApplicantLadgers.get(0).getEventInfo().getId());
    }

    public void createCancelPermitNotification(EventApplicantLadger eventApplicantLadger) {
        User eventApplicant = eventApplicantLadger.getEventApplicant();
        firebaseService.sendFCMNotificationMulticast(eventApplicant.getUserFcmTokens(), "이벤트 신청 취소", "이벤트 신청 취소되었습니다...\n사유를 확인해주세요");
        notificationService.save(eventApplicant, "이벤트 신청 취소되었습니다...\n사유를 확인해주세요", NotificationType.E, eventApplicantLadger.getEventInfo().getId());
    }

    public void createCommentNotificationToStaff(List<User> staffs, Long eventInfoId, CommentWriteRequestDto commentWriteRequestDTO){
        firebaseService.sendToTopic(eventInfoId+"-comment-staff-alert", "내가 주최한 이벤트에 댓글이 달렸어요!", "[댓글 미리보기] "+commentWriteRequestDTO.getContent());
        notificationService.saveBulk(staffs, "내가 주최한 이벤트에 댓글이 달렸어요!", NotificationType.C, eventInfoId);
    }
}
package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.EncryptionUtils;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.ladger.application.dto.request.QRCheckRequestDto;
import com.example.openoff.domain.ladger.application.dto.response.QRCheckResponseDto;
import com.example.openoff.domain.ladger.application.handler.EventApplicationLadgerHandler;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.notification.application.service.NotificationCreateService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class LadgerUpdateUseCase {
    private final UserUtils userUtils;
    private final EncryptionUtils encryptionUtils;
    private final EventStaffService eventStaffService;
    private final EventApplicantLadgerService eventApplicantLadgerService;
    private final EventApplicationLadgerHandler eventApplicationLadgerHandler;
    private final NotificationCreateService notificationCreateService;

    public void permitAndUpdateQRImageUrl(Long ladgerId) {
        User user = userUtils.getUser();
        EventApplicantLadger eventApplicantLadger = eventApplicantLadgerService.findLadgerInfo(ladgerId);
        if (eventApplicantLadger.getQrCodeImageUrl() != null) throw BusinessException.of(Error.ALREADY_PERMIT);
        // 처리하는 사람이 스탭인지 체크
        eventStaffService.checkEventStaff(user.getId(), eventApplicantLadger.getEventInfo().getId());
        eventApplicationLadgerHandler.ladgerPermitAndCreateQRImage(eventApplicantLadger);

        notificationCreateService.createApplyPermitNotification(eventApplicantLadger);
    }

    public void permitAndUpdateQRImageUrlAllApplicant(Long eventIndexId) {
        User user = userUtils.getUser();
        eventStaffService.checkEventStaff(user.getId(), eventIndexId);
        List<EventApplicantLadger> notAcceptedLadgersByEventIndex = eventApplicantLadgerService.findNotAcceptedLadgersByEventIndex(eventIndexId);
        eventApplicationLadgerHandler.totalLadgerPermitAndCreateQRImage(notAcceptedLadgersByEventIndex);

        notificationCreateService.createApplyPermitNotifications(notAcceptedLadgersByEventIndex);
    }

    public void cancelPermitedApplicantion(Long ladgerId) {
        User user = userUtils.getUser();
        EventApplicantLadger eventApplicantLadger = eventApplicantLadgerService.findLadgerInfo(ladgerId);
        if (!eventApplicantLadger.getIsAccept()) throw BusinessException.of(Error.NOT_ACCEPTED);
        // 처리하는 사람이 스탭인지 체크
        eventStaffService.checkEventStaff(user.getId(), eventApplicantLadger.getEventInfo().getId());
        eventApplicationLadgerHandler.removeQRImageAndUpdateIsAccepted(eventApplicantLadger);

        notificationCreateService.createCancelPermitNotification(eventApplicantLadger);
    }

    public QRCheckResponseDto checkQRCode(QRCheckRequestDto qrCheckRequestDto) {
        String decryptedContent = encryptionUtils.decrypt(qrCheckRequestDto.getContent());
        String[] parts = decryptedContent.split("\\.", 2);
        if (parts.length != 2) throw BusinessException.of(Error.INVALID_QR_CODE);
        String userId = parts[0];
        String ticketIndex = parts[1];
        EventApplicantLadger applicantLadger = eventApplicantLadgerService.findUserIdAndTicketIndexUpdateJoinAt(userId, ticketIndex);
        return QRCheckResponseDto.builder()
                .eventApplicantLadgerId(applicantLadger.getId())
                .userId(applicantLadger.getEventApplicant().getId())
                .joinAt(applicantLadger.getJoinAt())
                .ticketIndex(applicantLadger.getTicketIndex())
                .build();
    }
}

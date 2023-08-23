package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.EncryptionUtils;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.ladger.application.dto.request.QRCheckRequestDto;
import com.example.openoff.domain.ladger.application.dto.response.QRCheckResponseDto;
import com.example.openoff.domain.ladger.application.handler.EventApplicationLadgerHandler;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.notification.application.service.NotificationCreateUseCase;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class LadgerUpdateUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventIndexService eventIndexService;
    private final EncryptionUtils encryptionUtils;
    private final EventStaffService eventStaffService;
    private final EventApplicantLadgerService eventApplicantLadgerService;
    private final EventApplicationLadgerHandler eventApplicationLadgerHandler;
    private final NotificationCreateUseCase notificationCreateUseCase;

    public void permitAndUpdateQRImageUrl(Long ladgerId) {
        User user = userUtils.getUser();
        EventApplicantLadger eventApplicantLadger = eventApplicantLadgerService.findLadgerInfo(ladgerId);
        EventIndex eventIndex = eventApplicantLadger.getEventIndex();
        EventInfo eventInfo = eventApplicantLadger.getEventInfo();
        if (eventApplicantLadger.getQrCodeImageUrl() != null) throw BusinessException.of(Error.ALREADY_PERMIT);
        // 처리하는 사람이 스탭인지 체크
        eventStaffService.checkEventStaff(user.getId(), eventInfo.getId());

        Long approvedApplicantCount = eventApplicantLadgerService.countByEventIndex_IdAndIsAcceptTrue(eventIndex.getId());
        if (eventInfo.getEventMaxPeople().longValue() == (approvedApplicantCount+1)) {
            eventIndexService.updateOneEventIndexToClose(eventIndex);
            if (eventInfo.getEventIndexes().stream()
                    .filter(data -> !Objects.equals(data.getId(), eventIndex.getId()))
                    .allMatch(EventIndex::getIsClose)) {
                eventInfoService.suspensionEventApplication(eventInfo);
            }
        }

        eventApplicationLadgerHandler.ladgerPermitAndCreateQRImage(eventApplicantLadger);
        notificationCreateUseCase.createApplyPermitNotification(eventApplicantLadger);
    }

    public void permitAndUpdateQRImageUrlAllApplicant(Long eventIndexId) {
        User user = userUtils.getUser();
        Long eventInfoId = eventIndexService.findById(eventIndexId).getEventInfo().getId();
        eventStaffService.checkEventStaff(user.getId(), eventInfoId);
        List<EventApplicantLadger> notAcceptedLadgersByEventIndex = eventApplicantLadgerService.findNotAcceptedLadgersByEventIndex(eventIndexId);
        eventApplicationLadgerHandler.totalLadgerPermitAndCreateQRImage(notAcceptedLadgersByEventIndex);

        notificationCreateUseCase.createApplyPermitNotifications(notAcceptedLadgersByEventIndex);
    }

    public void cancelPermitedApplicantion(Long ladgerId) {
        User user = userUtils.getUser();
        EventApplicantLadger eventApplicantLadger = eventApplicantLadgerService.findLadgerInfo(ladgerId);
        if (!eventApplicantLadger.getIsAccept()) throw BusinessException.of(Error.NOT_ACCEPTED);
        // 처리하는 사람이 스탭인지 체크
        eventStaffService.checkEventStaff(user.getId(), eventApplicantLadger.getEventInfo().getId());

        if (eventApplicantLadger.getIsAccept() && eventApplicantLadger.getEventIndex().getIsClose()) {
            eventIndexService.updateOneEventIndexToOpen(eventApplicantLadger.getEventIndex());
            if (!eventApplicantLadger.getEventInfo().getEventApplyPermit()) {
                eventInfoService.resumeEventApplication(eventApplicantLadger.getEventInfo());
            }
        }
        eventInfoService.minusTotalRegisterCount(eventApplicantLadger.getEventInfo());
        eventApplicationLadgerHandler.removeQRImageAndUpdateIsAccepted(eventApplicantLadger);
        notificationCreateUseCase.createCancelPermitNotification(eventApplicantLadger);
    }

    public QRCheckResponseDto checkQRCode(QRCheckRequestDto qrCheckRequestDto) {
        User user = userUtils.getUser();
        Long eventInfoId = eventIndexService.findById(qrCheckRequestDto.getEventIndexId()).getEventInfo().getId();
        // 처리하는 사람이 스탭인지 체크
        eventStaffService.checkEventStaff(user.getId(), eventInfoId);

        String decryptedContent = encryptionUtils.decrypt(qrCheckRequestDto.getContent());
        String[] parts = decryptedContent.split("\\.", 2);
        if (parts.length != 2) throw BusinessException.of(Error.INVALID_QR_CODE);
        String userId = parts[0];
        String ticketIndex = parts[1];

        EventApplicantLadger applicantLadger = eventApplicantLadgerService.findUserIdAndTicketIndexUpdateJoinAt(userId, qrCheckRequestDto.getEventIndexId(), ticketIndex);
        return QRCheckResponseDto.builder()
                .eventApplicantLadgerId(applicantLadger.getId())
                .userId(applicantLadger.getEventApplicant().getId())
                .joinAt(applicantLadger.getJoinAt())
                .ticketIndex(applicantLadger.getTicketIndex())
                .build();
    }
}

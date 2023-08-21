package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.service.EventExtraAnswerService;
import com.example.openoff.domain.ladger.domain.entity.EventApplicantLadger;
import com.example.openoff.domain.ladger.domain.service.EventApplicantLadgerService;
import com.example.openoff.domain.notification.application.service.NotificationCreateService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class LadgerDeleteUseCase {
    private final UserUtils userUtils;
    private final NotificationCreateService notificationCreateService;
    private final EventApplicantLadgerService eventApplicantLadgerService;
    private final EventExtraAnswerService eventExtraAnswerService;


    public void deleteMyApplyLadger(Long ladgerId){
        User user = userUtils.getUser();
        EventApplicantLadger myEventTicketInfo = eventApplicantLadgerService.getApplicationInfo(ladgerId, user.getId());
        myEventTicketInfo.getEventInfo().updateTotalRegisterCountMinus();
        eventExtraAnswerService.deleteEventExtraAnswers(myEventTicketInfo.getEventIndex().getId());
        eventApplicantLadgerService.deleteEventApplicantLadger(myEventTicketInfo.getId());
    }

    public void deleteRejectLadger(Long ladgerId, String rejectReason) {
        User user = userUtils.getUser();
        EventApplicantLadger ladgerInfo = eventApplicantLadgerService.findLadgerInfo(ladgerId);
        List<String> staffId = ladgerInfo.getEventInfo().getEventStaffs().stream()
                .map(staff -> staff.getStaff().getId()).collect(Collectors.toList());
        if (!staffId.contains(user.getId())) {
            throw BusinessException.of(Error.EVENT_STAFF_NOT_FOUND);
        }
        ladgerInfo.getEventInfo().updateTotalRegisterCount();
        eventExtraAnswerService.deleteEventExtraAnswers(ladgerInfo.getEventIndex().getId());
        eventApplicantLadgerService.deleteEventApplicantLadger(ladgerId);
        notificationCreateService.createRejectApplyNotification(ladgerInfo, rejectReason);
    }
}

package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.ladger.application.dto.request.EventStaffCreateRequestDto;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import com.example.openoff.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class StaffCreateUseCase {
    private final UserUtils userUtils;
    private final UserFindService userFindService;
    private final EventInfoService eventInfoService;
    private final EventStaffService eventStaffService;
    private final FirebaseService firebaseService;

    @Transactional
    public void addNewSubStaff(EventStaffCreateRequestDto eventStaffCreateRequestDto){
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventStaffCreateRequestDto.getEventInfoId());
        // MAIN 스태프인지 체크
        eventStaffService.checkIsMainEventStaff(user.getId(), eventInfo.getId());

        User newStaff = userFindService.findByNickname(eventStaffCreateRequestDto.getNickname());
        eventStaffService.saveSubEventStaff(newStaff, eventInfo, newStaff.getNickname());
        newSubStaffSubscribeTopic(newStaff, eventInfo);
    }

    @Async
    public void newSubStaffSubscribeTopic(User newStaff, EventInfo eventInfo){
        List<UserFcmToken> userFcmTokens = newStaff.getUserFcmTokens();
        List<String> fcmTokens = userFcmTokens.stream().map(UserFcmToken::getFcmToken).collect(Collectors.toList());

        firebaseService.sendFCMNotificationMulticast(userFcmTokens, "주최자로 선정되었습니다.", "[ "+ eventInfo.getEventTitle() + " ] 이벤트 관리자로 추가되었습니다.\n참석 명단 관리 및 QR 티켓 승인이 가능합니다");

        if (!fcmTokens.isEmpty()) {
            firebaseService.subScribe(eventInfo.getId()+"-comment-staff-alert", fcmTokens);
            firebaseService.subScribe(eventInfo.getId()+"-permit-staff", fcmTokens);

            eventInfo.getEventIndexes().stream().map(EventIndex::getId).forEach(eventIndexId -> {
                firebaseService.subScribe(eventIndexId + "-check-approve-alert", fcmTokens);
                firebaseService.subScribe(eventIndexId + "-1day-staff-alert", fcmTokens);
                firebaseService.subScribe(eventIndexId + "-dday-staff-alert", fcmTokens);
                firebaseService.subScribe(eventIndexId + "-apply-half-staffAlert", fcmTokens);
            });
        }
    }
}

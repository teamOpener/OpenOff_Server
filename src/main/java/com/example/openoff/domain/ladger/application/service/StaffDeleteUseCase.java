package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
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
public class StaffDeleteUseCase {
    private final UserUtils userUtils;
    private final UserFindService userFindService;
    private final EventInfoService eventInfoService;
    private final EventStaffService eventStaffService;
    private final FirebaseService firebaseService;

    @Transactional
    public void deleteEventStaff(String staffName, Long eventInfoId){
        User user = userUtils.getUser();
        // MAIN 스태프인지 체크
        eventStaffService.checkIsMainEventStaff(user.getId(), eventInfoId);

        User removedStaff = userFindService.findByNickname(staffName);
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        eventStaffService.deleteSubEventStaff(removedStaff.getId(), eventInfoId);

        newSubStaffSubscribeTopic(removedStaff, eventInfo);
    }

    @Async
    public void newSubStaffSubscribeTopic(User removedStaff, EventInfo eventInfo){
        List<String> fcmTokens = removedStaff.getUserFcmTokens().stream().map(UserFcmToken::getFcmToken).collect(Collectors.toList());

        if (!fcmTokens.isEmpty()) {
            firebaseService.unSubscribe(eventInfo.getId()+"-comment-staff-alert", fcmTokens);
            firebaseService.unSubscribe(eventInfo.getId()+"-permit-staff", fcmTokens);

            eventInfo.getEventIndexes().stream().map(EventIndex::getId).forEach(eventIndexId -> {
                firebaseService.unSubscribe(eventIndexId + "-check-approve-alert", fcmTokens);
                firebaseService.unSubscribe(eventIndexId + "-1day-staff-alert", fcmTokens);
                firebaseService.unSubscribe(eventIndexId + "-dday-staff-alert", fcmTokens);
                firebaseService.unSubscribe(eventIndexId + "-apply-half-staffAlert", fcmTokens);
            });
        }
    }
}

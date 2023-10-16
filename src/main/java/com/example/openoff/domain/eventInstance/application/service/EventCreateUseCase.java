package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.infrastructure.fcm.FirebaseService;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.CreateNewEventResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.KakaoAddressResponse;
import com.example.openoff.domain.eventInstance.application.mapper.EventInstanceMapper;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventExtraQuestionService;
import com.example.openoff.domain.eventInstance.domain.service.EventImageService;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.interest.domain.exception.TooManyFieldException;
import com.example.openoff.domain.interest.domain.service.FieldService;
import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.entity.UserFcmToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class EventCreateUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventIndexService eventIndexService;
    private final EventImageService eventImageService;
    private final EventExtraQuestionService eventExtraQuestionService;
    private final FieldService fieldService;
    private final EventStaffService eventStaffService;
    private final KakaoLocalService kakaoLocalService;
    private final FirebaseService firebaseService;

    // TODO: 통째로 비동기처리 하기 -> mapper 에서 비동기 처리하기
    public CreateNewEventResponseDto createEvent(CreateNewEventRequestDto createNewEventRequestDto) {
        if(createNewEventRequestDto.getFieldTypeList().size() >= 4) {
            throw TooManyFieldException.of(Error.TOO_MANY_EVENT_FIELD);
        }
        final User user = userUtils.getUser();
        List<User> userList = userUtils.getUserList(createNewEventRequestDto.getStaffIdList());

        KakaoAddressResponse coord = kakaoLocalService.getKakaoAddressToCoord(createNewEventRequestDto.getStreetLoadAddress(), "similar");

        EventInfo eventInfo = eventInfoService.saveEventInfo(createNewEventRequestDto, coord);
        List<Long> eventIndexIdList = eventIndexService.saveEventIndex(eventInfo, createNewEventRequestDto.getEventDates());
        List<Long> eventImageIdList = eventImageService.saveEventImage(eventInfo, createNewEventRequestDto.getImageDataList());
        List<Long> eventExtraQuestionIdList = eventExtraQuestionService.saveEventExtraQuestion(eventInfo, createNewEventRequestDto.getExtraQuestionList());
        List<Long> eventInterestFieldIdList = fieldService.saveEventInterestFields(eventInfo, createNewEventRequestDto.getFieldTypeList());
        List<EventStaff> eventStaffs = eventStaffService.saveEventStaffs(user, userList, eventInfo, createNewEventRequestDto.getHostPhoneNumber(), createNewEventRequestDto.getHostEmail(), createNewEventRequestDto.getHostName());

        List<String> fcmTokens = eventStaffs.stream()
                .flatMap(eventStaff -> eventStaff.getStaff().getUserFcmTokens().stream())
                .map(UserFcmToken::getFcmToken)
                .collect(Collectors.toList());

        log.info("fcmTokens: {}", fcmTokens);

        List<UserFcmToken> staffsFcmList = userList.stream()
                .map(User::getUserFcmTokens)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (!staffsFcmList.isEmpty()) {
            fcmTokens.addAll(staffsFcmList.stream().map(UserFcmToken::getFcmToken).collect(Collectors.toList()));
            firebaseService.sendFCMNotificationMulticast(staffsFcmList, "주최자로 선정되었습니다.", "[ "+ createNewEventRequestDto.getTitle() + " ] 이벤트 관리자로 추가되었습니다.\n참석 명단 관리 및 QR 티켓 승인이 가능합니다");
        }

        if (!fcmTokens.isEmpty()) {
            firebaseService.subScribe(eventInfo.getId()+"-comment-staff-alert", fcmTokens);
            firebaseService.subScribe(eventInfo.getId()+"-permit-staff", fcmTokens);

            eventIndexIdList.forEach(eventIndexId -> {
                firebaseService.subScribe(eventIndexId + "-check-approve-alert", fcmTokens);
                firebaseService.subScribe(eventIndexId + "-1day-staff-alert", fcmTokens);
                firebaseService.subScribe(eventIndexId + "-dday-staff-alert", fcmTokens);
                firebaseService.subScribe(eventIndexId + "-apply-half-staffAlert", fcmTokens);
            });
        }

        return EventInstanceMapper.mapToEventInstanceInfoResponse(createNewEventRequestDto, eventInfo.getId(), eventIndexIdList, eventImageIdList, eventExtraQuestionIdList, eventInterestFieldIdList, eventStaffs.stream().map(EventStaff::getId).collect(Collectors.toList()));
    }
}

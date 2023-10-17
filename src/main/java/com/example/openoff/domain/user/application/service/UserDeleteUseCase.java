package com.example.openoff.domain.user.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.security.jwt.JwtProvider;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.auth.domain.service.SocialAccountService;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.ladger.domain.entity.StaffType;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.user.domain.entity.User;
import com.example.openoff.domain.user.domain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class UserDeleteUseCase {
    private final JwtProvider jwtProvider;
    private final UserUtils userUtils;
    private final SocialAccountService socialAccountService;
    private final UserQueryService userQueryService;
    private final EventInfoService eventInfoService;
    private final EventStaffService eventStaffService;


    public void withdrawal() {
        User user = userUtils.getUser();

        // redis 정보 선제적으로 삭제
        jwtProvider.removeRefreshToken(user.getId());

        List<EventStaff> eventStaffs = eventStaffService.findAllEventStaffByUserId(user.getId());

        List<EventStaff> mainStaff = eventStaffs.stream()
                .filter(eventStaff -> eventStaff.getStaffType().equals(StaffType.MAIN))
                .collect(Collectors.toList());

        // 메인 스태프인 경우 관련 테이블 정보까지 모두 삭제
        List<EventInfo> mainStaffEventInfoList = mainStaff.stream().map(EventStaff::getEventInfo).collect(Collectors.toList());
        eventStaffService.deleteEventStaffsByEntities(eventStaffs);
        mainStaffEventInfoList.forEach(eventInfo -> eventInfoService.deleteEventInfo(eventInfo.getId()));

        // 유저 정보 삭제
        userQueryService.deleteUserInfo(user);

        // social 계정 삭제
        if (user.getKakaoAccount() != null) socialAccountService.deleteSocialAccount(user.getKakaoAccount());
        if (user.getGoogleAccount() != null) socialAccountService.deleteSocialAccount(user.getGoogleAccount());
        if (user.getAppleAccount() != null) socialAccountService.deleteSocialAccount(user.getAppleAccount());
        if (user.getNormalAccount() != null) socialAccountService.deleteSocialAccount(user.getNormalAccount());
    }
}

package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class EventDeleteUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventStaffService eventStaffService;

    public void deleteEvent(Long eventInfoId) {
        User user = userUtils.getUser();
        eventStaffService.checkEventStaff(user.getId(), eventInfoId);
        eventInfoService.deleteEventInfo(eventInfoId);
    }
}

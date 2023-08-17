package com.example.openoff.domain.eventInstance.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.service.EventIndexService;
import com.example.openoff.domain.eventInstance.domain.service.EventInfoService;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional
public class EventUpdateUseCase {
    private final UserUtils userUtils;
    private final EventInfoService eventInfoService;
    private final EventIndexService eventIndexService;

    public void suspensionEventApplication(Long eventInfoId) {
        User user = userUtils.getUser();
        EventInfo eventInfo = eventInfoService.findEventInfoById(eventInfoId);
        if (eventInfo.getEventStaffs().stream()
                        .anyMatch(eventStaff -> eventStaff.getStaff().getId().equals(user.getId()))){
            eventInfoService.suspensionEventApplication(eventInfo);
            eventIndexService.updateEventIndexToClose(eventInfo);
        } else {
            throw BusinessException.of(Error.EVENT_STAFF_NOT_FOUND);
        }
    }
}

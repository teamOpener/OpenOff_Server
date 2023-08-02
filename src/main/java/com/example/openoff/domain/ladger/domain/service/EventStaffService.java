package com.example.openoff.domain.ladger.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.ladger.domain.entity.EventStaff;
import com.example.openoff.domain.ladger.domain.entity.StaffType;
import com.example.openoff.domain.ladger.domain.repository.EventStaffRepository;
import com.example.openoff.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventStaffService {
    private final EventStaffRepository eventStaffRepository;

    public Long saveEventStaff(User user, EventInfo eventInfo, String phoneNumber, String email, String name) {
        return eventStaffRepository.save(EventStaff.toEntity(user, eventInfo, StaffType.MAIN, phoneNumber, email, name)).getId();
    }

    public void checkEventStaff(String eventStaffId, Long eventInfoId) {
        if (!eventStaffRepository.existsByEventInfo_IdAndStaff_Id(eventInfoId, eventStaffId)){
            throw BusinessException.of(Error.EVENT_STAFF_NOT_FOUND);
        }
    }

    public List<String> getEventStaffIds(Long eventInfoId){
        return eventStaffRepository.findEventStaffByEventInfo_Id(eventInfoId).stream().map(staff -> staff.getStaff().getId()).collect(Collectors.toList());
    }
}

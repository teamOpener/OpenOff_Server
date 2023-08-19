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

    public List<EventStaff> saveEventStaffs(User user, List<User> staffs, EventInfo eventInfo, String phoneNumber, String email, String name) {
        EventStaff mainStaff = EventStaff.toEntity(user, eventInfo, StaffType.MAIN, phoneNumber, email, name);
        List<EventStaff> eventStaffs = staffs.stream().map(staff -> EventStaff.toEntity(staff, eventInfo, StaffType.SUB, phoneNumber, email, name)).collect(Collectors.toList());
        eventStaffs.add(0, mainStaff);
        return eventStaffRepository.saveAll(eventStaffs);
    }

    public void checkEventStaff(String eventStaffId, Long eventInfoId) {
        if (!eventStaffRepository.existsByEventInfo_IdAndStaff_Id(eventInfoId, eventStaffId)){
            throw BusinessException.of(Error.EVENT_STAFF_NOT_FOUND);
        }
    }

    public List<String> getEventStaffIds(Long eventInfoId){
        return eventStaffRepository.findEventStaffByEventInfo_Id(eventInfoId).stream().map(staff -> staff.getStaff().getId()).collect(Collectors.toList());
    }

    public List<EventStaff> getEventStaffs(Long eventInfoId){
        return eventStaffRepository.findEventStaffByEventInfo_Id(eventInfoId);
    }
}

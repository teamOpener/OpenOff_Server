package com.example.openoff.domain.ladger.application.service;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.domain.ladger.application.dto.response.EventStaffInfoResponseDto;
import com.example.openoff.domain.ladger.domain.service.EventStaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class StaffSearchUseCase {
    private final EventStaffService eventStaffService;

    public List<EventStaffInfoResponseDto> getEventStaffList(Long eventInfoId){
        return eventStaffService.getEventStaffs(eventInfoId).stream()
                .map(staff -> EventStaffInfoResponseDto.from(staff.getId(), staff.getStaffType(), staff.getStaff().getId(), staff.getName()))
                .collect(Collectors.toList());
    }
}

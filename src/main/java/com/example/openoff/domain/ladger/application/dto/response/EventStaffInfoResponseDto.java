package com.example.openoff.domain.ladger.application.dto.response;

import com.example.openoff.domain.ladger.domain.entity.StaffType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventStaffInfoResponseDto {
    private Long eventStaffId;
    private StaffType staffType;
    private String userId;
    private String staffName;

    @Builder
    public EventStaffInfoResponseDto(Long eventStaffId, StaffType staffType, String userId, String staffName) {
        this.eventStaffId = eventStaffId;
        this.staffType = staffType;
        this.userId = userId;
        this.staffName = staffName;
    }

    public static EventStaffInfoResponseDto from(Long eventStaffId, StaffType staffType, String userId, String staffName){
        return EventStaffInfoResponseDto.builder()
                .eventStaffId(eventStaffId)
                .staffType(staffType)
                .userId(userId)
                .staffName(staffName)
                .build();
    }
}

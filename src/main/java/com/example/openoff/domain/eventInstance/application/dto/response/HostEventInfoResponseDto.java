package com.example.openoff.domain.eventInstance.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class HostEventInfoResponseDto {
    private Long eventInfoId;
    private Boolean isApproved;
    private String eventTitle;
    private List<FieldType> fieldTypeList;
    private String eventPeriod;
    private List<EventIndexInfo> eventIndexInfoList;

    @Getter
    @Builder
    public static class EventIndexInfo {
        private Long eventIndexId;
        private LocalDateTime eventDate;

        public static EventIndexInfo of(Long eventIndexId, LocalDateTime eventDate) {
            return EventIndexInfo.builder()
                    .eventIndexId(eventIndexId)
                    .eventDate(eventDate)
                    .build();
        }
    }

    @Builder
    public HostEventInfoResponseDto(Long eventInfoId, Boolean isApproved, String eventTitle, String eventPeriod,
                                    List<EventIndexInfo> eventIndexInfoList, List<FieldType> fieldTypeList) {
        this.eventInfoId = eventInfoId;
        this.isApproved = isApproved;
        this.eventTitle = eventTitle;
        this.eventPeriod = eventPeriod;
        this.eventIndexInfoList = eventIndexInfoList;
        this.fieldTypeList = fieldTypeList;
    }
}

package com.example.openoff.domain.ladger.application.dto.response;

import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApplicationInfoResponseDto {
    private Long eventInfoId;
    private String eventTitle;
    private List<LocalDateTime> eventDateList;
    private List<FieldType> fieldTypeList;
    private List<EventApplicantLadgerInfo> eventApplicantLadgerInfoList;

    @Getter
    @Builder
    public static class EventApplicantLadgerInfo {
        private Long eventApplicantLadgerId;
        private String ticketIndex;

        public static EventApplicantLadgerInfo of(Long eventApplicantLadgerId, String ticketIndex) {
            return EventApplicantLadgerInfo.builder()
                    .eventApplicantLadgerId(eventApplicantLadgerId)
                    .ticketIndex(ticketIndex)
                    .build();
        }
    }

    @Builder
    public ApplicationInfoResponseDto(Long eventInfoId, String eventTitle,
                                      List<LocalDateTime> eventDateList, List<FieldType> fieldTypeList,
                                        List<EventApplicantLadgerInfo> eventApplicantLadgerInfoList) {
        this.eventInfoId = eventInfoId;
        this.eventTitle = eventTitle;
        this.eventDateList = eventDateList;
        this.fieldTypeList = fieldTypeList;
        this.eventApplicantLadgerInfoList = eventApplicantLadgerInfoList;
    }
}

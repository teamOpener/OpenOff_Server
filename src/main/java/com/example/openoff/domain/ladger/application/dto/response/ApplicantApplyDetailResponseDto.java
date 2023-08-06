package com.example.openoff.domain.ladger.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApplicantApplyDetailResponseDto {
    private String username;
    private String birth;
    private Long eventInfoId;
    private Long eventIndexId;
    private String eventTitle;
    private String streetRoadAddress;
    private LocalDateTime eventDate;
    private Boolean isAccepted;
    private List<QnAInfo> qnAInfoList;

    @Getter
    @Builder
    public static class QnAInfo {
        private String question;
        private String answer;

        public static QnAInfo of(String question, String answer) {
            return QnAInfo.builder()
                    .question(question)
                    .answer(answer)
                    .build();
        }
    }

    @Builder
    public ApplicantApplyDetailResponseDto(String username, String birth, Long eventInfoId, Long eventIndexId, String eventTitle,
                                           String streetRoadAddress, LocalDateTime eventDate, Boolean isAccepted, List<QnAInfo> qnAInfoList)
    {
        this.username = username;
        this.birth = birth;
        this.eventInfoId = eventInfoId;
        this.eventIndexId = eventIndexId;
        this.eventTitle = eventTitle;
        this.streetRoadAddress = streetRoadAddress;
        this.eventDate = eventDate;
        this.isAccepted = isAccepted;
        this.qnAInfoList = qnAInfoList;
    }
}

package com.example.openoff.domain.ladger.application.dto.response;

import com.example.openoff.domain.user.domain.entity.GenderType;
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
    private GenderType genderType;
    private Long eventInfoId;
    private Long eventIndexId;
    private String eventTitle;
    private String streetRoadAddress;
    private LocalDateTime eventDate;
    private Boolean isAccepted;
    private Boolean isJoined;
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
    public ApplicantApplyDetailResponseDto(String username, String birth, GenderType genderType, Long eventInfoId, Long eventIndexId, String eventTitle,
                                           String streetRoadAddress, LocalDateTime eventDate, Boolean isAccepted, Boolean isJoined, List<QnAInfo> qnAInfoList)
    {
        this.username = username;
        this.birth = birth;
        this.genderType = genderType;
        this.eventInfoId = eventInfoId;
        this.eventIndexId = eventIndexId;
        this.eventTitle = eventTitle;
        this.streetRoadAddress = streetRoadAddress;
        this.eventDate = eventDate;
        this.isAccepted = isAccepted;
        this.isJoined = isJoined;
        this.qnAInfoList = qnAInfoList;
    }
}

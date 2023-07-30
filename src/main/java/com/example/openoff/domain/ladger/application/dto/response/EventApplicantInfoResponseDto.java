package com.example.openoff.domain.ladger.application.dto.response;

import com.example.openoff.domain.user.domain.entity.GenderType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EventApplicantInfoResponseDto {
    private String userId;
    private String username;
    private String birth;
    private GenderType genderType;
    private Long ladgerId;
    private Boolean isAccepted;
    private LocalDateTime createdAt;

    @Builder
    public EventApplicantInfoResponseDto(String userId, String username, String birth, GenderType genderType, Long ladgerId, Boolean isAccepted, LocalDateTime createdAt){
        this.userId = userId;
        this.username = username;
        this.birth = birth;
        this.genderType = genderType;
        this.ladgerId = ladgerId;
        this.isAccepted = isAccepted;
        this.createdAt = createdAt;
    }
}

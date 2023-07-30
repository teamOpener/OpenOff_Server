package com.example.openoff.domain.ladger.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QRCheckResponseDto {
    private Long eventApplicantLadgerId;
    private String userId;
    private String ticketIndex;
    private LocalDateTime joinAt;

    @Builder
    public QRCheckResponseDto(Long eventApplicantLadgerId, String userId, String ticketIndex, LocalDateTime joinAt) {
        this.eventApplicantLadgerId = eventApplicantLadgerId;
        this.userId = userId;
        this.ticketIndex = ticketIndex;
        this.joinAt = joinAt;
    }
}

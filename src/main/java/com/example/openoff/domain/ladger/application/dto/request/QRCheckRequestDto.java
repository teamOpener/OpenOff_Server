package com.example.openoff.domain.ladger.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QRCheckRequestDto {
    private Long eventApplicantLadgerId;
    private String ticketIndex;
    private String content;

    @Builder
    public QRCheckRequestDto(Long eventApplicantLadgerId, String ticketIndex, String content) {
        this.eventApplicantLadgerId = eventApplicantLadgerId;
        this.ticketIndex = ticketIndex;
        this.content = content;
    }
}

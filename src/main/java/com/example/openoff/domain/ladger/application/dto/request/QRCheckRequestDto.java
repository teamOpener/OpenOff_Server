package com.example.openoff.domain.ladger.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QRCheckRequestDto {
    private Long eventIndexId;
    private String content;

    @Builder
    public QRCheckRequestDto(Long eventIndexId, String content) {
        this.eventIndexId = eventIndexId;
        this.content = content;
    }
}

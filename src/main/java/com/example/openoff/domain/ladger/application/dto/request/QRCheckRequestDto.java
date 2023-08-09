package com.example.openoff.domain.ladger.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QRCheckRequestDto {
    private String content;

    @Builder
    public QRCheckRequestDto(String content) {
        this.content = content;
    }
}

package com.example.openoff.domain.ladger.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventStaffCreateRequestDto {
    private String nickname;
    private Long eventInfoId;
}

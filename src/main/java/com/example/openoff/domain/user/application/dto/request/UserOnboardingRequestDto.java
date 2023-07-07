package com.example.openoff.domain.user.application.dto.request;

import com.example.openoff.domain.user.domain.entity.GenderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOnboardingRequestDto {
    private String nickname;
    private String username;
    private GenderType gender;
    private Integer year;
    private Integer month;
    private Integer day;
}

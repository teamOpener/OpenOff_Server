package com.example.openoff.domain.auth.application.dto.response.apple;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppleUserInfoResponseDto {

    private String platformId;
    private String email;
}
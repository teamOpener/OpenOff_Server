package com.example.openoff.domain.auth.application.dto.response.apple;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppleTokenResponse {

    private String token;
    private String email;
    private Boolean isRegistered;
    private String platformId;
}

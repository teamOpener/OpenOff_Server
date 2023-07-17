package com.example.openoff.domain.auth.application.dto.request.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRequestDto {

    private String accessToken;

    private String refreshToken;


    public static TokenRequestDto of(String accessToken, String refreshToken) {
        TokenRequestDto responseDto = new TokenRequestDto();
        responseDto.accessToken = accessToken;
        responseDto.refreshToken = refreshToken;

        return responseDto;
    }
}

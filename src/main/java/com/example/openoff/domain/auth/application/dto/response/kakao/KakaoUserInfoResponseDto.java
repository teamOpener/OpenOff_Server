package com.example.openoff.domain.auth.application.dto.response.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoResponseDto {
    /**
     * issuer ex https://kauth.kakao.com
     */
    private String iss;
    /**
     * client id
     */
    private String aud;
    /**
     * oauth provider account unique id
     */
    private String sub;

    private String email;
}

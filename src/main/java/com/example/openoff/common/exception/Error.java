package com.example.openoff.common.exception;

import lombok.Getter;

@Getter
public enum Error {
    DATA_NOT_FOUND("데이터를 찾을 수 없습니다.", 800),

    // OAuth
    GOOGLE_OAUTH_FAILED("Google에서 토큰을 검색하지 못했습니다.", 900),
    GOOGLE_OAUTH_FAILED2("Google에서 사용자 정보를 검색하지 못했습니다.", 901),
    KAKAO_OAUTH_FAILED("KAKAO에서 토큰을 검색하지 못했습니다.", 910),
    KAKAO_OAUTH_FAILED2("KAKAO에서 사용자 정보를 검색하지 못했습니다.", 911),
    APPLE_OIDC_FAILED("Apple OAuth Identity Token 형식이 올바르지 않습니다.", 920),
    APPLE_OIDC_FAILED2("Apple OAuth 로그인 중 Identity Token 유효기간이 만료됐습니다.", 921),
    APPLE_OIDC_FAILED3("Apple OAuth Identity Token 값이 올바르지 않습니다.", 922),
    APPLE_OIDC_FAILED4("Apple JWT 값의 alg, kid 정보가 올바르지 않습니다.", 923),
    APPLE_OIDC_FAILED5("Apple OAuth 통신 암호화 과정 중 문제가 발생했습니다.", 924),
    APPLE_OIDC_FAILED6("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.", 925),
    APPLE_OIDC_FAILED7("Apple OAuth Claims 값이 올바르지 않습니다.", 926),

    // 사용자
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 1000),


    // JWT
    INVALID_TOKEN("잘못된 토큰 요청", 7000),
    EXPIRED_TOKEN("토큰 만료", 7001);

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
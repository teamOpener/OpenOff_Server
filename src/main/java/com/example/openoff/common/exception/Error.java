package com.example.openoff.common.exception;

import lombok.Getter;

@Getter
public enum Error {
    DATA_NOT_FOUND("데이터를 찾을 수 없습니다.", 900),

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

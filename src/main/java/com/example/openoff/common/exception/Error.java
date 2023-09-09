package com.example.openoff.common.exception;

import lombok.Getter;

@Getter
public enum Error {
    INTERNAL_SERVER_ERROR("서버 내부 에러입니다.", 500),

    // JWT
    INVALID_TOKEN("잘못된 토큰 요청", 600),
    EXPIRED_TOKEN("토큰 만료되었습니다. 토큰 재발행 혹은 로그인을 다시 해주세요", 601),

    DATA_NOT_FOUND("데이터를 찾을 수 없습니다.", 800),
    FILE_UPLOAD_ERROR("s3 upload 오류입니다.", 801),
    FILE_EXTENTION_ERROR("파일 확장자 오류입니다.", 802),
    FILE_DELETE_ERROR("파일 삭제 오류입니다.", 803),

    // OAuth
    OAUTH_FAILED("잘못된 소셜 로그인 요청입니다. KAKAO, GOOGLE, APPLE 연동만이 가능합니다.", 890),
    GOOGLE_OAUTH_FAILED("Google에서 토큰을 검색하지 못했습니다.", 900),
    GOOGLE_OAUTH_FAILED2("Google에서 사용자 정보를 검색하지 못했습니다.", 901),
    KAKAO_OAUTH_FAILED("KAKAO 토큰이 만료되었습니다.", 910),
    KAKAO_OAUTH_FAILED2("KAKAO 토큰이 올바르지 않습니다.", 911),
    APPLE_OIDC_FAILED("Apple OAuth Identity Token 형식이 올바르지 않습니다.", 920),
    APPLE_OIDC_FAILED2("Apple OAuth 로그인 중 Identity Token 유효기간이 만료됐습니다.", 921),
    APPLE_OIDC_FAILED3("Apple OAuth Identity Token 값이 올바르지 않습니다.", 922),
    APPLE_OIDC_FAILED4("Apple JWT 값의 alg, kid 정보가 올바르지 않습니다.", 923),
    APPLE_OIDC_FAILED5("Apple OAuth 통신 암호화 과정 중 문제가 발생했습니다.", 924),
    APPLE_OIDC_FAILED6("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.", 925),
    APPLE_OIDC_FAILED7("Apple OAuth Claims 값이 올바르지 않습니다.", 926),
    EMAIL_DUPLICATION("이미 가입된 일반 계정입니다.", 930),
    NCP_SMS_FAILED("SMS 전송 에러입니다. 다시 시도해주세요", 950),


    // 사용자
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 1000),
    USER_NOT_CORRECT_SMS_NUM("잘못된 인증 번호입니다.", 1001),
    USER_NICKNAME_DUPLICATION("중복된 닉네임입니다.", 1002),
    USER_PHONE_NUM_DUPLICATION("이미 가입된 휴대폰 번호에요🥹🥹🥹", 1003),


    // Field
    TOO_MANY_INTEREST("관심 분야를 4개 이상 설정할 수 없습니다.", 1100),
    INVALID_FIELD_TYPE("잘못된 분야 상수 값 입니다.", 1101),

    // EventInstance
    TOO_MANY_EVENT_FIELD("이벤트 분야를 4개 이상 설정할 수 없습니다.", 1200),
    ALREADY_APPLIED_EVENT("이미 신청한 이벤트입니다.", 1201),
    EVENT_DATE_IS_BEFORE("이벤트 날짜가 지나 접근할 수 없습니다.", 1202),

    // Ladger
    EVENT_APPLICANT_FULL("이벤트 신청 인원이 가득 찻습니다.", 1300),

    EVENT_STAFF_NOT_FOUND("해당 이벤트의 스테프가 아닙니다.", 1301),
    EVENT_APPLICANT_NOT_FOUND("신청 데이터가 존재하지 않습니다", 1302),
    ALREADY_PERMIT("이미 신청이 완료되었습니다.", 1303),
    INVALID_QR_CODE("유효한 QR 코드가 아닙니다.", 1304),
    NOT_ACCEPTED("승인된 신청이 아닙니다.", 1305),
    EVENT_APPLICANT_NOT_ACCEPT("승인되지 않은 인원입니다.", 1306),
    EVENT_CLOSED("해당 날짜는 더이상 신청할 수 없습니다.", 1307),
    NOT_MAIN_STAFF("해당 이벤트의 메인 스테프가 아닙니다.", 1308),

    // Comment
    NOT_DELETE_AUTHORITY("댓글 삭제 권한이 없습니다.", 1400),
    ALREADY_JOIN("이미 신청한 인원입니다.", 9000);


    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}

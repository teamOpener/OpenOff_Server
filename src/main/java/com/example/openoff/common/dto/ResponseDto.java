package com.example.openoff.common.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> {
    private int code;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    @Builder
    public ResponseDto(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <T> ResponseDto<T> of(int code, String message, T data) {
        return ResponseDto.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}

package com.example.openoff.common.exception.dto;

import com.example.openoff.common.exception.BusinessException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;

    public static ErrorResponse from(BusinessException e) {
        return new ErrorResponse(e.getError().getErrorCode(), e.getError().getMessage(), e.getHttpStatus(), LocalDateTime.now());
    }
}

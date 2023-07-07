package com.example.openoff.common.exception.handler;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorResponse> handlerCustomException(BusinessException e) {
//        log.error("Status: {}, Message: {}", e.getHttpStatus(), e.getMessage());
        log.error("Message: {}", e.getMessage());
        log.error("Custom Error Code: {}, Message: {}", e.getError().getErrorCode(), e.getError().getMessage());

        return ResponseEntity
                .status(e.getError().getErrorCode())
                .body(ErrorResponse
                        .builder()
                        .code(e.getError().getErrorCode())
                        .message(e.getError().getMessage())
//                        .httpStatus(e.getHttpStatus())
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}

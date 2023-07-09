package com.example.openoff.common.exception;

import com.example.openoff.common.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ResponseDto responseDto;
//    private final Error error;


//    public BusinessException(Error error) {
//        this.error = error;
//    }

    @Builder
    public BusinessException(ResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public static BusinessException of(Error error) {
        return BusinessException.builder()
                .responseDto(ResponseDto.of(error.getErrorCode(), error.getMessage(), null))
                .build();
    }
}

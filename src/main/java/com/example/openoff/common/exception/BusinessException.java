package com.example.openoff.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final Error error;

//    public BusinessException(String message, Error error) {
//        super(message);
//        this.error = error;
//    }

    public BusinessException(Error error) {
        this.error = error;
    }

//    @Builder
//    public BusinessException(HttpStatus httpStatus, String message, Error error) {
//        super(message);
//        this.httpStatus = httpStatus;
//        this.error = error;
//    }
//
//    @Builder
//    public BusinessException(HttpStatus httpStatus, Error error) {
//        this.httpStatus = httpStatus;
//        this.error = error;
//    }
}

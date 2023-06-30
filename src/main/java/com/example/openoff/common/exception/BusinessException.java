package com.example.openoff.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final Error error;

    public BusinessException(HttpStatus httpStatus, String message, Error error) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public BusinessException(Error error, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
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

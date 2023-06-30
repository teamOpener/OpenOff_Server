package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import org.springframework.http.HttpStatus;

public class JwtException extends BusinessException {
    public JwtException(Error error, HttpStatus httpStatus) {
        super(error, httpStatus);
    }
}

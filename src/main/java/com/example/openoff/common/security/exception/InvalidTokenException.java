package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.Error;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends JwtException{
    public InvalidTokenException(Error error, HttpStatus httpStatus) {
        super(error, httpStatus);
    }
}

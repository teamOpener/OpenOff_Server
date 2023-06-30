package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.Error;
import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends JwtException{
    public TokenNotFoundException(Error error, HttpStatus httpStatus) {
        super(error, httpStatus);
    }
}

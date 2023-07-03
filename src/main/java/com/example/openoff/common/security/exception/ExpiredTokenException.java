package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.Error;

public class ExpiredTokenException extends JwtException{
    public ExpiredTokenException(Error error) {
        super(error);

}

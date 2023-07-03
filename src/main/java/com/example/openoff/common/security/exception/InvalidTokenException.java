package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.Error;

public class InvalidTokenException extends JwtException{
    public InvalidTokenException(Error error) {
        super(error);

}

package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.Error;

public class TokenNotFoundException extends JwtException{
    public TokenNotFoundException(Error error) {
        super(error);

}

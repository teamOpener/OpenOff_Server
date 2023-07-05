package com.example.openoff.common.security.exception;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

public class JwtException extends BusinessException {
    public JwtException(Error error) {
        super(error);
    }
}

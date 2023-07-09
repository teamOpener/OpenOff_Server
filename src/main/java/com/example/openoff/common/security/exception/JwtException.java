package com.example.openoff.common.security.exception;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

public class JwtException extends BusinessException {
    public JwtException(Error error) {
        super(ResponseDto.of(error.getErrorCode(), error.getMessage(), null));
    }
}

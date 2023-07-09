package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

public class OAuthException extends BusinessException {
    public OAuthException(Error error) {
        super(ResponseDto.of(error.getErrorCode(), error.getMessage(), null));
    }
}

package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

public class OAuthException extends BusinessException {
    public OAuthException(Error error) {
        super(error);
    }
}

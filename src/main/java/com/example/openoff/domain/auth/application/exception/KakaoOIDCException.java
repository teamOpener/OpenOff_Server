package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.exception.Error;

public class KakaoOIDCException extends OAuthException {
    public KakaoOIDCException(Error error) {
        super(error);
    }
}

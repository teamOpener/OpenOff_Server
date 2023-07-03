package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.exception.Error;

public class KakaoOAuthException extends OAuthException {
    public KakaoOAuthException(Error error) {
        super(error);
    }
}

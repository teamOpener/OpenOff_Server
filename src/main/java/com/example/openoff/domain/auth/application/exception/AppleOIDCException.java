package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.exception.Error;

public class AppleOIDCException extends OAuthException {
    public AppleOIDCException(Error error) {
        super(error);
    }
}

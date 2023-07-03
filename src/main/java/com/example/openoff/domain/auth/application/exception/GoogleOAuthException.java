package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.exception.Error;

public class GoogleOAuthException extends OAuthException {
    public GoogleOAuthException(Error error) {
        super(error);
    }
}

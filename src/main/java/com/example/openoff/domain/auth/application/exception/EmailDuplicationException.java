package com.example.openoff.domain.auth.application.exception;

import com.example.openoff.common.exception.Error;

public class EmailDuplicationException extends OAuthException {
    public EmailDuplicationException(Error error) {
        super(error);
    }
}

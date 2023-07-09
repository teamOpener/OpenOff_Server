package com.example.openoff.domain.user.domain.exception;

import com.example.openoff.common.exception.Error;

public class UserNotCorrectSMSNumException extends UserException{
    public UserNotCorrectSMSNumException(Error error) {
        super(error);
    }
}

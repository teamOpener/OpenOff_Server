package com.example.openoff.domain.user.domain.exception;

import com.example.openoff.common.exception.Error;

public class UserNotFoundException extends UserException{
    public UserNotFoundException(Error error) {
        super(error);
    }
}

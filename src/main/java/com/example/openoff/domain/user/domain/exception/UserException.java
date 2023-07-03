package com.example.openoff.domain.user.domain.exception;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

public class UserException extends BusinessException {
    public UserException(Error error) {
        super(error);
    }
}

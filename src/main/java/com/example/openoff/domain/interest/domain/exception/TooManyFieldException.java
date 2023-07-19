package com.example.openoff.domain.interest.domain.exception;

import com.example.openoff.common.exception.Error;

public class TooManyFieldException extends FieldException {
    public TooManyFieldException(Error error) {
        super(error);
    }
}

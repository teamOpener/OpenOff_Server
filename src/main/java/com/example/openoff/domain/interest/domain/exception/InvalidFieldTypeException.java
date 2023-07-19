package com.example.openoff.domain.interest.domain.exception;

import com.example.openoff.common.exception.Error;

public class InvalidFieldTypeException extends FieldException {
    public InvalidFieldTypeException(Error error) {
        super(error);
    }
}

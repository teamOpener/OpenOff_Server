package com.example.openoff.common.infrastructure.domain;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

public class EnumConverter<E extends Enum<E> & CodeValue> implements AttributeConverter<E, String> {

    private Class<E> clz;

    protected EnumConverter(Class<E> enumClass){
        this.clz = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(clz).stream()
                .filter(e->e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(()-> new BusinessException(Error.DATA_NOT_FOUND));
    }
}

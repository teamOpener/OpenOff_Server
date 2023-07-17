package com.example.openoff.domain.interest.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class FieldTypeConverter extends EnumConverter<FieldType> {
    FieldTypeConverter(){
        super(FieldType.class);
    }
}

package com.example.openoff.domain.user.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter extends EnumConverter<GenderType> {
    GenderTypeConverter(){
        super(GenderType.class);
    }
}

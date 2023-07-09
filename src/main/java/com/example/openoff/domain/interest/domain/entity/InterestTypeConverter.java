package com.example.openoff.domain.interest.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class InterestTypeConverter extends EnumConverter<InterestType> {
    InterestTypeConverter(){
        super(InterestType.class);
    }
}

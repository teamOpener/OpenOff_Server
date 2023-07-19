package com.example.openoff.domain.ladger.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class StaffTypeConverter extends EnumConverter<StaffType> {
    StaffTypeConverter(){
        super(StaffType.class);
    }
}

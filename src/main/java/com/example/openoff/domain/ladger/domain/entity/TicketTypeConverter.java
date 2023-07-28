package com.example.openoff.domain.ladger.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketTypeConverter extends EnumConverter<TicketType> {
    TicketTypeConverter(){
        super(TicketType.class);
    }
}

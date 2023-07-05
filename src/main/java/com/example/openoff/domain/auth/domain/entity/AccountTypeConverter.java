package com.example.openoff.domain.auth.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeConverter extends EnumConverter<AccountType> {
    AccountTypeConverter(){
        super(AccountType.class);
    }
}

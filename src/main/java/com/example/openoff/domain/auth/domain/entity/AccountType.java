package com.example.openoff.domain.auth.domain.entity;

import com.example.openoff.common.infrastructure.domain.CodeValue;

public enum AccountType implements CodeValue {
    GOOGLE("G","구글"),
    KAKAO("K", "카카오"),
    APPLE("A", "애플"),
    NOMAL("N","일반");

    private String code;
    private String value;

    AccountType(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getValue() {
        return value;
    }
}

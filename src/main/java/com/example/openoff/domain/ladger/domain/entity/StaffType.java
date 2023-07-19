package com.example.openoff.domain.ladger.domain.entity;

import com.example.openoff.common.infrastructure.domain.CodeValue;

public enum StaffType implements CodeValue {
    MAIN("M", "주최자"),
    SUB("S","일반스텝");

    private String code;
    private String value;

    StaffType(String code, String value) {
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

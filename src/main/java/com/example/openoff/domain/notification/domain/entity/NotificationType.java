package com.example.openoff.domain.notification.domain.entity;

import com.example.openoff.common.infrastructure.domain.CodeValue;

public enum NotificationType implements CodeValue {
    E("Event", "이벤트 관련"),
    C("Comment", "댓글 관련");

    private String code;
    private String value;

    NotificationType(String code, String value) {
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

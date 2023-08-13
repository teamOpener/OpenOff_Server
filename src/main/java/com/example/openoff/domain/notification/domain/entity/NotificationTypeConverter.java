package com.example.openoff.domain.notification.domain.entity;

import com.example.openoff.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class NotificationTypeConverter extends EnumConverter<NotificationType> {
    NotificationTypeConverter(){
        super(NotificationType.class);
    }
}

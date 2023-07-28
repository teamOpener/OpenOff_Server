package com.example.openoff.domain.ladger.domain.entity;

import com.example.openoff.common.infrastructure.domain.CodeValue;

import java.util.concurrent.ThreadLocalRandom;

public enum TicketType implements CodeValue {
    A("A", "A"),
    B("B","B"),
    C("C","C"),
    D("D","D"),
    E("E","E"),
    F("F","F");

    private String code;
    private String value;

    TicketType(String code, String value) {
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

    public static TicketType getRandomTicketType() {
        TicketType[] ticketTypes = TicketType.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(ticketTypes.length);
        return ticketTypes[randomIndex];
    }
}

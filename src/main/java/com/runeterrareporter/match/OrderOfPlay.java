package com.runeterrareporter.match;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderOfPlay {
    FIRST(0),
    SECOND(1);

    private final int value;

    OrderOfPlay(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}

package com.sengul.biddingapinew.infrastructure.utils.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ADMIN,
    REGULAR;

    @JsonValue
    public String getName() {
        return name();
    }
}
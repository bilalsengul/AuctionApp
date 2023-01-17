package com.sengul.biddingapinew.infrastructure.utils.enums;

public enum HttpHeaders {
    X_USER_ID("x-user-id"),
    CONTENT_TYPE("Content-Type"),
    ACCEPT("Accept"),
    AUTHORIZATION("Authorization"),
    ORIGIN("Origin");

    private final String key;

    HttpHeaders(String key) {
        this.key = key;
    }

    public final String key() {
        return key;
    }
}
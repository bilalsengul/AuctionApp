package com.sengul.biddingapinew.application.response.builder;

import com.sengul.biddingapinew.application.response.Response;

public class ResponseBuilder<T> {

    private T data;
    private String message;

    public ResponseBuilder<T> withData(T data) {
        this.data = data;
        return this;
    }

    public ResponseBuilder<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public Response<T> build() {
        return new Response<>(data, message);
    }
}
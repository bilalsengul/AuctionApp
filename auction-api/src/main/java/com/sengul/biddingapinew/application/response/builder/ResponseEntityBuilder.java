package com.sengul.biddingapinew.application.response.builder;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ResponseEntityBuilder<T> {

    private T response;
    private HttpStatus status;

    public ResponseEntityBuilder<T> withResponse(T response) {
        this.response = response;
        return this;
    }

    public ResponseEntityBuilder<T> withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseEntity<T> build() {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(status.value()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

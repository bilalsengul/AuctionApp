package com.sengul.biddingapinew.application.controller;

import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.LoginRequest;
import com.sengul.biddingapinew.application.response.LoginResponse;
import com.sengul.biddingapinew.application.response.builder.ResponseEntityBuilder;
import com.sengul.biddingapinew.domain.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global")
public class GlobalController {

    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws UserNotFoundException {
        LoginResponse loginResponse = loginService.login(request);
        return new ResponseEntityBuilder<LoginResponse>()
                .withStatus(HttpStatus.OK)
                .withResponse(loginResponse)
                .build();
    }
}
package com.sengul.biddingapinew.domain.service;

import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.global.LoginRequest;
import com.sengul.biddingapinew.application.response.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest request) throws UserNotFoundException;
}
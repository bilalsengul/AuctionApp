package com.sengul.biddingapinew.domain.service;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.UpdateUserRequest;
import com.sengul.biddingapinew.domain.model.User;

public interface UserService {
    User get(String id) throws UserNotFoundException;

    User update(String id, UpdateUserRequest request) throws UserNotFoundException, BadRequestException;
}
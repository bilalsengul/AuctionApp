package com.sengul.biddingapinew.domain.service;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.user.UpdateUserRequest;
import com.sengul.biddingapinew.domain.model.Bid;
import com.sengul.biddingapinew.domain.model.Item;
import com.sengul.biddingapinew.domain.model.User;

import java.util.List;

public interface UserService {
    User get(String id) throws UserNotFoundException;

    User update(String id, UpdateUserRequest request) throws UserNotFoundException, BadRequestException;

    void addNewItem(String id, Item item) throws UserNotFoundException;

    List<Bid> getBids(String id) throws UserNotFoundException;

    void refundBidsOnItem(String id, String itemId) throws UserNotFoundException;
}
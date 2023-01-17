package com.sengul.biddingapinew.domain.service;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.ItemNotFoundException;
import com.sengul.biddingapinew.application.exception.UserNotFoundException;
import com.sengul.biddingapinew.application.request.item.BidOnItemRequest;
import com.sengul.biddingapinew.application.request.item.CreateItemRequest;
import com.sengul.biddingapinew.application.request.item.SearchItemRequest;
import com.sengul.biddingapinew.application.request.item.UpdateItemRequest;
import com.sengul.biddingapinew.application.response.SearchItemResponse;
import com.sengul.biddingapinew.domain.model.Bid;
import com.sengul.biddingapinew.domain.model.Item;

import java.util.List;

public interface ItemService {
    Item create(CreateItemRequest request);

    Item get(String id) throws ItemNotFoundException;

    Item update(String id, UpdateItemRequest request) throws ItemNotFoundException, BadRequestException;

    void delete(String id) throws ItemNotFoundException;

    SearchItemResponse search(SearchItemRequest request);

    List<Bid> getBids(String id);

    void bidOn(String id, BidOnItemRequest request) throws ItemNotFoundException, BadRequestException, UserNotFoundException;

}
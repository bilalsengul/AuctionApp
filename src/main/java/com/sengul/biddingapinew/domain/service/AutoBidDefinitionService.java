package com.sengul.biddingapinew.domain.service;

import com.sengul.biddingapinew.application.exception.BadRequestException;
import com.sengul.biddingapinew.application.exception.ItemNotFoundException;
import com.sengul.biddingapinew.application.request.autobid.AutoBidOnItemRequest;
import com.sengul.biddingapinew.domain.model.AutoBidDefinition;

public interface AutoBidDefinitionService {
    AutoBidDefinition create(String itemId, AutoBidOnItemRequest request) throws ItemNotFoundException, BadRequestException;
}
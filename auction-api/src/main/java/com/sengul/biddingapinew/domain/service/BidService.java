package com.sengul.biddingapinew.domain.service;

import com.sengul.biddingapinew.application.request.bid.CreateBidRequest;
import com.sengul.biddingapinew.domain.model.Bid;

public interface BidService {
    Bid create(CreateBidRequest request);
}

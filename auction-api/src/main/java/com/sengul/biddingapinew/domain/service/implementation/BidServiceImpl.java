package com.sengul.biddingapinew.domain.service.implementation;

import com.sengul.biddingapinew.application.request.bid.CreateBidRequest;
import com.sengul.biddingapinew.domain.model.Bid;
import com.sengul.biddingapinew.domain.service.BidService;
import com.sengul.biddingapinew.infrastructure.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    @Override
    public Bid create(CreateBidRequest request) {
        log.info("Started handling request= " + request);

        Bid newBid = new Bid(
                request.getUserId(),
                request.getItemId(),
                request.getPrice()
        );

        bidRepository.save(newBid);

        log.info("Finished handling request= " + request);
        return newBid;
    }
}
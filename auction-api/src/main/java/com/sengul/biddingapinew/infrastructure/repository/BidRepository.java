package com.sengul.biddingapinew.infrastructure.repository;

import com.sengul.biddingapinew.domain.model.Bid;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends MongoRepository<Bid, String> {
    List<Bid> findByItemId(String itemId);

    List<Bid> findByUserId(String userId);
}
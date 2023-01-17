package com.sengul.biddingapinew.infrastructure.repository;

import com.sengul.biddingapinew.domain.model.AutoBidDefinition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoBidDefinitionRepository extends MongoRepository<AutoBidDefinition, String> {

    List<AutoBidDefinition> findByUserId(String userId);
}
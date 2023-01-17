package com.sengul.biddingapinew.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoBidDefinition {
    @Id
    private String id;
    private String userId;
    private String itemId;
    private Double budgetPercentage;
    private Double budget;
    private Double balance;
    private Double bidAmount;
    private boolean active;
    private Long createdDate;

    public AutoBidDefinition(String userId, String itemId, Double bidAmount) {
        this.id = UUID.randomUUID().toString();
        this.budgetPercentage = 0.0;
        this.budget = 0.0;
        this.balance = 0.0;
        this.bidAmount = bidAmount;
        this.active = false;
        this.userId = userId;
        this.itemId = itemId;
        this.createdDate = System.currentTimeMillis();
    }
}
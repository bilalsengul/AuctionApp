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
public class Bid {
    @Id
    private String id;
    private String userId;
    private String itemId;
    private Double price;
    private Long createdDate;

    public Bid(String userId, String itemId, Double price) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.itemId = itemId;
        this.price = price;
        this.createdDate = System.currentTimeMillis();
    }
}
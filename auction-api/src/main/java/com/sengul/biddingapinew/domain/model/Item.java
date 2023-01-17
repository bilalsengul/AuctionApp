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
public class Item {
    @Id
    private String id;
    private String name;
    private String image;
    private String description;
    private Double openingPrice;
    private Double currentPrice;
    private Double minimumAutoBidPrice;
    private Boolean onAuction;
    private Long auctionEndDate;
    private Long createdDate;
    private Long updatedDate;

    public Item(String name, String description, Double openingPrice) {
        this.id = UUID.randomUUID().toString();
        this.image = null;
        this.name = name;
        this.onAuction = false;
        this.auctionEndDate = null;
        this.description = description;
        this.openingPrice = openingPrice;
        this.currentPrice = openingPrice;
        this.minimumAutoBidPrice = 50.0;
        this.createdDate = System.currentTimeMillis();
        this.updatedDate = System.currentTimeMillis();
    }

    public Item(String name, String description, String image, Double openingPrice, Double minimumAutoBidPrice, boolean onAuction, Long auctionEndDate) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.image = image;
        this.openingPrice = openingPrice;
        this.currentPrice = openingPrice;
        this.minimumAutoBidPrice = minimumAutoBidPrice;
        this.onAuction = onAuction;
        this.auctionEndDate = auctionEndDate;
        this.createdDate = System.currentTimeMillis();
        this.updatedDate = System.currentTimeMillis();
    }
}
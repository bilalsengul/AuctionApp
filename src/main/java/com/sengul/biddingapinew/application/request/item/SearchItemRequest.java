package com.sengul.biddingapinew.application.request.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemRequest {

    private String keyword = "";

    private Boolean searchInDescription = false;

    private Boolean exactMatch = false;

    private Boolean withExpired = false;

    private Boolean sortByPriceDescending = null;

    private int currentPage = 1;

    private int size = 10;
}
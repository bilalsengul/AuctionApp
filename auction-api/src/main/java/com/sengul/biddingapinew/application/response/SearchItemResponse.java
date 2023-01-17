package com.sengul.biddingapinew.application.response;

import com.sengul.biddingapinew.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemResponse {
    private int size;
    private int currentPage;
    private int count;
    private Long total;
    private List<Item> result;
}
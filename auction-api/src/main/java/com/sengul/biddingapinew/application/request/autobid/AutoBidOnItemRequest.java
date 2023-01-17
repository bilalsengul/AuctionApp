package com.sengul.biddingapinew.application.request.autobid;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoBidOnItemRequest {
    @NotNull(message = "bidPrice cannot be null")
    @Positive(message = "bidPrice must be positive double")
    private Double bidPrice;

    private String userId;
}
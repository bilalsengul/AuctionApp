package com.sengul.biddingapinew.application.request.bid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBidRequest {

    @NotNull(message = "userId cannot be null")
    @NotBlank(message = "userId cannot be empty string")
    private String userId;

    @NotNull(message = "itemId cannot be null")
    @NotBlank(message = "itemId cannot be empty string")
    private String itemId;

    @NotNull(message = "price cannot be null")
    @Positive(message = "price must be positive double")
    private Double price;
}
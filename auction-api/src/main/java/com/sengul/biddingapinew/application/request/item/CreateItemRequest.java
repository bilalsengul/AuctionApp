package com.sengul.biddingapinew.application.request.item;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be empty string")
    private String name;

    @NotNull(message = "image cannot be null")
    @NotBlank(message = "image cannot be empty string")
    private String image;

    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be empty string")
    private String description;

    @NotNull(message = "openingPrice cannot be null")
    @Positive(message = "openingPrice must be positive double")
    private Double openingPrice;

    @NotNull(message = "minimumAutoBidPrice cannot be null")
    @Positive(message = "minimumAutoBidPrice must be positive double")
    private Double minimumAutoBidPrice;

    @Nullable
    private boolean onAuction = false;

    @Nullable
    private Long auctionEndDate = null;
}
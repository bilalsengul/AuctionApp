package com.sengul.biddingapinew.application.request.item;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemRequest {

    @Nullable
    @NotBlank(message = "name cannot be empty string")
    private String name;

    @Nullable
    @NotBlank(message = "imagePath cannot be empty string")
    private String imagePath;

    @Nullable
    @NotBlank(message = "description cannot be empty string")
    private String description;

    @Nullable
    @Positive(message = "openingPrice must be positive double")
    private Double openingPrice;

    @Nullable
    @Positive(message = "currentPrice must be positive double")
    private Double currentPrice;

    @Nullable
    private Boolean onAuction;

    @Nullable
    private Long auctionEndDate;

    public boolean anyFullNonUpdatableFieldWhileOnAuction() {
        return (this.name != null && !this.name.isBlank())
                || (this.imagePath != null && !this.imagePath.isBlank())
                || (this.description != null && !this.description.isBlank())
                || (this.openingPrice != null);
    }
}
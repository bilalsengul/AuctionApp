package com.sengul.biddingapinew.application.request.user;

import com.mongodb.lang.Nullable;
import com.sengul.biddingapinew.domain.model.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @Nullable
    @NotBlank(message = "name cannot be empty string")
    private String name;

    @Nullable
    @NotBlank(message = "surname cannot be empty string")
    private String surname;

    @Nullable
    @NotBlank(message = "password cannot be empty string")
    private String password;

    @Nullable
    @NotBlank(message = "email cannot be empty string")
    private String email;

    @Nullable
    @Positive(message = "budget must be positive double")
    private Double budget;

    @Nullable
    @Positive(message = "balance must be positive double")
    private Double balance;

    @Nullable
    @NotEmpty(message = "items cannot be empty list")
    private List<Item> items;

    @Nullable
    @Positive(message = "autoBidBudget must be positive double")
    private Double autoBidBudget;

    @Nullable
    @Positive(message = "autoBidBalance must be positive double")
    private Double autoBidBalance;

    @Nullable
    @Positive(message = "autoBidBalanceNotificationThreshold must be positive double")
    private Double autoBidBalanceNotificationThreshold;

    public static UpdateUserRequest balanceRequest(Double balance) {
        return new UpdateUserRequest(null, null, null, null, null, balance, null, null, null, null);
    }
}
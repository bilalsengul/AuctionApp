package com.sengul.biddingapinew.application.request.user;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static UpdateUserRequest balanceRequest(Double balance) {
        return new UpdateUserRequest(null, null, null, null, null, balance);
    }
}
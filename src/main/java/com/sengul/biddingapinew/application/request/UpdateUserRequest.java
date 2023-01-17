package com.sengul.biddingapinew.application.request;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
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
}
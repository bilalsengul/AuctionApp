package com.sengul.biddingapinew.application.request.global;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "userName cannot be null")
    @NotBlank(message = "userName cannot be empty string")
    private String username;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be empty string")
    private String password;
}
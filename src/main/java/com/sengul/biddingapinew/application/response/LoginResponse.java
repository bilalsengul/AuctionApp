package com.sengul.biddingapinew.application.response;

import com.sengul.biddingapinew.infrastructure.utils.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {

    private String id;
    private String username;
    private String name;
    private String surname;
    private List<UserRole> roles;
    private String email;
}
package com.sengul.biddingapinew.domain.model;

import com.sengul.biddingapinew.infrastructure.utils.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String email;
    private Double budget;
    private Double balance;
    private List<UserRole> roles;
    private List<Item> items;
    private Long createdDate;
    private Long updatedDate;

    public User(String username, String password, String name, String surname, String email, Double budget, List<UserRole> roles) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.budget = budget;
        this.balance = budget;
        this.roles = roles;
        this.items = null;
        this.createdDate = System.currentTimeMillis();
        this.updatedDate = System.currentTimeMillis();
    }

    public void addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }
}
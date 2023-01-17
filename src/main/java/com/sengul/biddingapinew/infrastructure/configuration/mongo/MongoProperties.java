package com.sengul.biddingapinew.infrastructure.configuration.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoProperties {
    String host;
    String port;
    String database;
    String username;
    String password;
    String authenticationDatabase;
}
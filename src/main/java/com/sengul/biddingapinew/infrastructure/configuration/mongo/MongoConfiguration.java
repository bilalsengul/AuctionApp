package com.sengul.biddingapinew.infrastructure.configuration.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.sengul.biddingapinew.infrastructure.repository")
@EnableConfigurationProperties(MongoProperties.class)
@RequiredArgsConstructor
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private final MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return "biddingapi";
    }

    @Override
    public MongoClient mongoClient() {
        String conString = "mongodb://" +
                mongoProperties.username +
                ":" +
                mongoProperties.password +
                "@" +
                mongoProperties.host +
                ":" +
                mongoProperties.port +
                "/" +
                mongoProperties.database +
                "?authSource=" +
                mongoProperties.authenticationDatabase;

        ConnectionString connectionString = new ConnectionString(conString);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}
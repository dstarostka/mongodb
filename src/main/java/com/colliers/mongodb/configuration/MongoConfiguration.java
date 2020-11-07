package com.colliers.mongodb.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
class MongoConfiguration {

    private MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017/database");
    }

    @Bean MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "database");
    }
}
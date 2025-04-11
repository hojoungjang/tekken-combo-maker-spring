package com.github.hojoungjang.seeder;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.github.hojoungjang.tekken_combo_maker.move")
@EnableMongoRepositories(basePackages = "com.github.hojoungjang.tekken_combo_maker.move")
@EnableMongoAuditing
@Slf4j
public class MoveDataSeederConfig {

    private final Dotenv dotenv = Dotenv.configure()
            .directory("env")
            .filename(".env.dev")
            .load();

    @Bean
    MongoClient mongoClient() {
        String mongoUser = dotenv.get("MONGODB_USER");
        String mongoPassword = dotenv.get("MONGODB_PASSWORD");
        String mongoUri = dotenv.get("MONGODB_CLUSTER_URI");
        String connString = String.format("mongodb+srv://%s:%s@%s", mongoUser, mongoPassword, mongoUri);
        return MongoClients.create(connString);
    }

    @Bean
    MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, dotenv.get("MONGODB_DATABASE"));
    }
}

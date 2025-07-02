package com.kk.services.impl;

import com.kk.models.Tweet;
import com.kk.services.TweetsSource;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class InAppTweetsSimulatorImpl implements TweetsSource {

    private static final List<String> USERS = List.of(
            "alice", "bob", "charlie", "dave", "emma"
    );

    private static final List<String> MESSAGES = List.of(
            "Learning Kafka is fun!",
            "Spring Boot makes microservices easy.",
            "Just deployed a new service 🚀",
            "ElasticSearch is powerful for search use cases.",
            "Kafka + Spring = ❤️"
    );

    private static final List<String> HASHTAGS = List.of(
            "#kafka", "#springboot", "#elasticsearch", "#microservices", "#cloud"
    );

    private final Random random = new Random();

    @Override
    public List<Tweet> fetchTweets() {
        return List.of(generateTweet(), generateTweet(), generateTweet());
    }

    private Tweet generateTweet() {
        String id = UUID.randomUUID().toString();
        String username = USERS.get(random.nextInt(USERS.size()));
        String message = MESSAGES.get(random.nextInt(MESSAGES.size()));
        long timestamp = Instant.now().toEpochMilli();
        List<String> hashtags = List.of(
                HASHTAGS.get(random.nextInt(HASHTAGS.size()))
        );

        return new Tweet(id, username, message, timestamp, hashtags);
    }
}


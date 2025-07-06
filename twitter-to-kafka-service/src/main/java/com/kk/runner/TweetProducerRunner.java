package com.kk.runner;

import com.kk.configuration.KafkaConfigurationData;
import com.kk.models.Tweet;
import com.kk.services.TweetsSource;
import com.kk.avro.TweetAvroModel;
import com.kk.kafka.producer.service.KafkaProducer;
import com.kk.transformer.TwitterStatusToAvroTransformer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TweetProducerRunner {

    private final KafkaConfigurationData kafkaConfigData;
    private final TweetsSource tweetsSource;
    private final TwitterStatusToAvroTransformer transformer;
    private final KafkaProducer<Long, TweetAvroModel> kafkaProducer;

    public TweetProducerRunner(KafkaConfigurationData kafkaConfigData,
                               TweetsSource tweetsSource,
                               TwitterStatusToAvroTransformer transformer,
                               KafkaProducer<Long, TweetAvroModel> kafkaProducer) {
        this.kafkaConfigData = kafkaConfigData;
        this.tweetsSource = tweetsSource;
        this.transformer = transformer;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void produceTweets() {
        List<Tweet> tweets = tweetsSource.fetchTweets();
        tweets.forEach(tweet -> {
            TweetAvroModel model = transformer.getTwitterAvroModelFromStatus(tweet);
            kafkaProducer.send(kafkaConfigData.getTopicName(), tweet.getId().hashCode() & 0xfffffffL, model);
        });
    }
}

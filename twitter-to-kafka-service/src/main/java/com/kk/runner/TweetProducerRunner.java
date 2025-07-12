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
    private final int initialDelay = 10000;
    private final int fixedRate = 5000;
    private final long hashCode = 0xfffffff;

    /**
     * Constructor of TweetProducerRunner.
     * @param kafkaConfigData KafkaConfigurationData
     * @param tweetsSource TweetsSource
     * @param transformer TwitterStatusToAvroTransformer
     * @param kafkaProducer KafkaProducer<Long, TweetAvroModel>
     */
    public TweetProducerRunner(final KafkaConfigurationData kafkaConfigData,
                               final TweetsSource tweetsSource,
                               final TwitterStatusToAvroTransformer transformer,
                               final KafkaProducer<Long, TweetAvroModel> kafkaProducer) {
        this.kafkaConfigData = kafkaConfigData;
        this.tweetsSource = tweetsSource;
        this.transformer = transformer;
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * Produce tweets.
     */
    @Scheduled(initialDelay = initialDelay, fixedRate = fixedRate)
    public void produceTweets() {
        final List<Tweet> tweets = tweetsSource.fetchTweets();
        tweets.forEach(tweet -> {
            TweetAvroModel model = transformer.getTwitterAvroModelFromStatus(tweet);
            kafkaProducer.send(kafkaConfigData.getTopicName(), tweet.getId().hashCode() & hashCode, model);
        });
    }
}

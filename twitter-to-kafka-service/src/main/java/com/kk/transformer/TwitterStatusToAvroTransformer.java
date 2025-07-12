package com.kk.transformer;

import com.kk.avro.TweetAvroModel;
import com.kk.models.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TwitterStatusToAvroTransformer {

    /**
     * Get TweetAvroModel from status.
     * @param tweet Tweet
     * @return TweetAvroModel
     */
    public TweetAvroModel getTwitterAvroModelFromStatus(final Tweet tweet) {
        return TweetAvroModel.newBuilder()
                .setId(tweet.getId())
                .setUsername(tweet.getUsername())
                .setMessage(tweet.getMessage())
                .setTimestamp(tweet.getTimestamp())
                .setHashtags(tweet.getHashtags())
                .build();
    }
}

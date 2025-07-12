package com.kk.services.impl;

import com.kk.models.Tweet;
import com.kk.services.TweetsSource;

import java.util.List;

public class XstreamSimulatorImpl implements TweetsSource {

    /**
     * Get tweets.
     * @return List<Tweet>
     */
    @Override
    public List<Tweet> fetchTweets() {
        return List.of();
    }
}

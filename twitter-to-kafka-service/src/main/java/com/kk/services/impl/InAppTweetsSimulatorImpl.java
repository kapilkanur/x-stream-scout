package com.kk.services.impl;

import com.kk.models.Tweet;
import com.kk.services.TweetsSource;

import java.util.List;

public class InAppTweetsSimulatorImpl implements TweetsSource {

    @Override
    public List<Tweet> fetchTweets() {
        return List.of();
    }

}

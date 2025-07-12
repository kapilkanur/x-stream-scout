package com.kk.services;

import com.kk.models.Tweet;

import java.util.List;

public interface TweetsSource {

    /**
     * Fetch tweets.
     * @return List<Tweet>
     */
    List<Tweet> fetchTweets();

}

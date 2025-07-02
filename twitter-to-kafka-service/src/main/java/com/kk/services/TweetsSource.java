package com.kk.services;

import com.kk.models.Tweet;

import java.util.List;

public interface TweetsSource {

    List<Tweet> fetchTweets();

}

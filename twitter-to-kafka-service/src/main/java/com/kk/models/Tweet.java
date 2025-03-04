package com.kk.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    private String id;
    private String username;
    private String message;
    private long timestamp;
    private List<String> hashtags;

}
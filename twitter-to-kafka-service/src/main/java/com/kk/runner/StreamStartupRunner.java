package com.kk.runner;

import com.kk.init.StreamInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StreamStartupRunner implements CommandLineRunner {

    private final StreamInitializer streamInitializer;

    public StreamStartupRunner(StreamInitializer streamInitializer) {
        this.streamInitializer = streamInitializer;
    }

    @Override
    public void run(String... args) {
        streamInitializer.init();
    }
}


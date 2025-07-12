package com.kk.runner;

import com.kk.init.StreamInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StreamStartupRunner implements CommandLineRunner {

    private final StreamInitializer streamInitializer;

    /**
     * StreamStartupRunner constructor.
     * @param streamInitializer StreamInitializer
     */
    public StreamStartupRunner(final StreamInitializer streamInitializer) {
        this.streamInitializer = streamInitializer;
    }

    /**
     * Run method.
     * @param args
     */
    @Override
    public void run(final String... args) {
        streamInitializer.init();
    }
}


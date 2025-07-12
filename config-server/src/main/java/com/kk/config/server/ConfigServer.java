package com.kk.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServer {

    /**
     * Main method.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}

package com.urlmapping.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ApiRunner implements ApplicationRunner {


    private final UrlMappingClient client;
    private static final Logger logger = LoggerFactory.getLogger(ApiRunner.class);


    public ApiRunner(UrlMappingClient client) {
        this.client = client;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {


        String randomURLToTest = UUID.randomUUID().toString().substring(0, 7);
        String randomAliasToTest = UUID.randomUUID().toString().substring(0, 4);
        var createdUrlResponse = client.createShortenURL(randomURLToTest, randomAliasToTest);
        logger.info("Shortened URL Created: {}", createdUrlResponse);


    }

}

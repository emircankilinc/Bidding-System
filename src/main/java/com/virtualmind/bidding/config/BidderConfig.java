package com.virtualmind.bidding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class BidderConfig {

    @Value("${bidders}")
    private List<String> bidderUrls;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public List<String> getBidderUrls() {
        return bidderUrls;
    }
}

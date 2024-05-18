package com.exchange.task.exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class CurrencyApiConfiguration {


    @Bean("currencyApi")
    RestClient restClient(RestClient.Builder restClientBuilder) {
        //TODO url to properties
       return restClientBuilder.baseUrl("http://api.nbp.pl/api")
               .defaultUriVariables(Map.of("format","json"))
               .build();
    }

}

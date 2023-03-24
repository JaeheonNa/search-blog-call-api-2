package com.naver.searchblogcallnaverapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${naver.baseUri}")
    private String naverBaseUri;

    @Bean(name="naverWebClient")
    public WebClient naverWebClient(){
        return WebClient.builder()
                .baseUrl(this.naverBaseUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build()
                .mutate()
                .build();
    }
}

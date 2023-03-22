package com.naver.searchblogcallnaverapi.serviceImpl;

import com.naver.searchblogcallnaverapi.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchServiceImpl_naver_001 implements BlogSearchService {

    private String reqUri = "https://openapi.naver.com/v1/search/blog.json";

    private String clientId = "A6Kk8FWlTT51IPPsutdZ";

    private String clientSecret = "U6ez5hu5fu";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map getBlogsFromApi(String query, String sort, int page, int size){
        Map response   = null;

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(this.reqUri)
                .queryParam("query", query)
                .queryParam("sort", sort)
                .queryParam("size", size)
                .queryParam("page", page)
                .encode(StandardCharsets.UTF_8)
                .build(false);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", this.clientId);
        headers.add("X-Naver-Client-Secret", this.clientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity<>(headers);

        response = restTemplate.exchange(uriBuilder.toUri(), HttpMethod.GET, entity, Map.class).getBody();
        return response;
    }

}

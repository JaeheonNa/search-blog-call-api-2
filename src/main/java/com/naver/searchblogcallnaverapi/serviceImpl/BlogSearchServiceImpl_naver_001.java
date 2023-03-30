package com.naver.searchblogcallnaverapi.serviceImpl;

import com.naver.searchblogcallnaverapi.dto.*;
import com.naver.searchblogcallnaverapi.service.BlogSearchService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchServiceImpl_naver_001 implements BlogSearchService {

    @Value("${naver.clientId}")
    private String clientId;
    @Value("${naver.clientSecret}")
    private String clientSecret;
    @Qualifier("naverWebClient")
    private final WebClient webClient;


    @Override
    public KakaoResponse getBlogsFromApi(String query, String sort, int page, int size){
        log.info("Naver 요청 들어옴.");
        String naverSort;
        if("accuracy".equals(sort)) naverSort = "sim";
        else naverSort = "date";

        Mono<NaverResponse> naverResponseMono =  webClient.get().uri(uriBuilder1 ->
                        uriBuilder1.path("/v1/search/blog.json")
                                .queryParam("query", query)
                                .queryParam("sort", naverSort)
                                .queryParam("start", page)
                                .queryParam("display", size)
                                .build()
                ).headers(headers -> {
                    headers.add("X-Naver-Client-Id", this.clientId);
                    headers.add("X-Naver-Client-Secret", this.clientSecret);
                }).accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(NaverResponse.class);
        return makeKakaoResponseFormat(naverResponseMono);
    }

    private KakaoResponse makeKakaoResponseFormat(Mono<NaverResponse> response) {
        NaverResponse naverResponse = response.share().block();

        List<Documents> documents = naverResponse.getItems().stream().map(NaverResponse::convert2KakaoResponse).collect(Collectors.toList());

        Meta meta = Meta.builder()
                .total_count(naverResponse.getTotal())
                .is_end(naverResponse.getStart()*naverResponse.getDisplay() >= naverResponse.getTotal())
                .build();

        KakaoResponse kakaoResponse = KakaoResponse.builder()
                .documents(documents)
                .meta(meta)
                .build();

        return kakaoResponse;
    }

}

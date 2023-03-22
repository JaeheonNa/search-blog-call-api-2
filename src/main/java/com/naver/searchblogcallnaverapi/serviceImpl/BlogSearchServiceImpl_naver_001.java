package com.naver.searchblogcallnaverapi.serviceImpl;

import com.naver.searchblogcallnaverapi.dto.Documents;
import com.naver.searchblogcallnaverapi.dto.Item;
import com.naver.searchblogcallnaverapi.dto.NaverResponse;
import com.naver.searchblogcallnaverapi.service.BlogSearchService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchServiceImpl_naver_001 implements BlogSearchService {

    @Value("${naver.clientId}")
    private String clientId;

    @Value("${naver.clientSecret}")
    private String clientSecret;
    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build()
                .mutate()
                .build();
    }

    @Override
    public Map getBlogsFromApi(String query, String sort, int page, int size){
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

        Map kakaoResponseMono= makeKakaoResponseFormat(naverResponseMono);
        return kakaoResponseMono;
    }

    private Map makeKakaoResponseFormat(Mono<NaverResponse> response) {
        Map responseMap = new HashMap();
        NaverResponse naverResponse = response.share().block();

        List documents = new ArrayList();
        for(Item item : naverResponse.getItems()){
            Documents document = new Documents();
            document.setUrl(item.getLink());
            document.setContents(item.getDescription());
            document.setBlogname(item.getBloggername());
            document.setTitle(item.getTitle());
            document.setThumbnail(null);
            document.setDatetime(item.getPostdate());
            documents.add(document);
        }
        responseMap.put("documents", documents);


        Map<String, Object> meta = new HashMap<>();
        meta.put("total_count", naverResponse.getTotal());
        meta.put("is_end", naverResponse.getStart()*naverResponse.getDisplay() >= naverResponse.getTotal());
        meta.put("pageable_count", null);
        responseMap.put("meta", meta);
        return responseMap;
    }

}

package com.naver.searchblogcallnaverapi.controller;

import com.naver.searchblogcallnaverapi.service.BlogSearchService;
import com.naver.searchblogcallnaverapi.serviceFactory.BlogSearchServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("search/blog")
public class BlogSearchController {

    private final BlogSearchServiceFactory blogSearchServiceFactory;

    @GetMapping()
    public Map getBlogByKeywordFromKakao(@RequestParam(name = "query") String query
                                 , @RequestParam(name = "sort", required = false, defaultValue = "accuracy") String sort
                                 , @RequestParam(name = "page", required = false, defaultValue = "1") int page
                                 , @RequestParam(name = "size", required = false, defaultValue = "10") int size
                                 , @RequestParam(name = "apiType", required = false, defaultValue = "kakao") String apiType) {
        /* 특정 API를 호출하는 Service 객체를 가져온다. */
        BlogSearchService blogSearchService = blogSearchServiceFactory.getBlogSearchService(apiType);
        /* Service 객체를 통해 API를 조회한다. */
        Map blogSearchResult = blogSearchService.getBlogsFromApi(query, sort, page, size);
        /* 랭킹을 관리하는 Service 객체를 가져온다. */
        return blogSearchResult;
    }

}

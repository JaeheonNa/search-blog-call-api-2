package com.naver.searchblogcallnaverapi.serviceFactory;

import com.naver.searchblogcallnaverapi.service.BlogSearchService;
import com.naver.searchblogcallnaverapi.serviceImpl.BlogSearchServiceImpl_naver_001;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlogSearchServiceFactory {
    private final BlogSearchServiceImpl_naver_001 blogSearchServiceImpl_naver_001;

    public BlogSearchService getBlogSearchService(String apiType){
        return blogSearchServiceImpl_naver_001;
    }



}

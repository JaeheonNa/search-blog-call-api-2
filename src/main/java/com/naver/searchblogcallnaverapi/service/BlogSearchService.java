package com.naver.searchblogcallnaverapi.service;

import com.naver.searchblogcallnaverapi.dto.KakaoResponse;
import com.naver.searchblogcallnaverapi.dto.NaverResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BlogSearchService {
    KakaoResponse getBlogsFromApi(String query, String sort, int page, int size);
}

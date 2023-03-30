package com.naver.searchblogcallnaverapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class KakaoResponse {
    List<Documents> documents;
    Meta meta;
}

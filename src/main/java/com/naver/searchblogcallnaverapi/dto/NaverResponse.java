package com.naver.searchblogcallnaverapi.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class NaverResponse {

    List<Item> items;
    String lastBuildDate;
    int total;
    int start;
    int display;

    public static Documents convert2KakaoResponse(Item item){
        return Documents.builder()
                .url(item.getLink())
                .contents(item.getDescription())
                .blogname(item.getBloggername())
                .title(item.getTitle())
                .thumbnail(null)
                .datetime(item.getPostdate())
                .build();
    }
}

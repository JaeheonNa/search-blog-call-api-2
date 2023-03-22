package com.naver.searchblogcallnaverapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverResponse {

    List<Item> items;
    String lastBuildDate;
    int total;
    int start;
    int display;
}

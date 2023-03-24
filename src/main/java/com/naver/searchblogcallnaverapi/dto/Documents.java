package com.naver.searchblogcallnaverapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Documents {
    String blogname;
    String contents;
    String datetime;
    String thumbnail;
    String title;
    String url;
}

package com.naver.searchblogcallnaverapi.dto;

import lombok.Data;

@Data
public class Meta {

    int total_count;
    int pageable_count;
    boolean is_end;
}
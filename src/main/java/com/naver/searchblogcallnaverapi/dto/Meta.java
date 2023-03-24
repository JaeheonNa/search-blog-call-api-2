package com.naver.searchblogcallnaverapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Meta {

    int total_count;
    int pageable_count;
    boolean is_end;
}

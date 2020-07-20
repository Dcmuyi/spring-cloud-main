package com.muyi.servicestudy.entity.response;

import lombok.Data;

@Data
public class Page {
    private Long page_start;

    private Long page_number;

    private Long page_count;
}

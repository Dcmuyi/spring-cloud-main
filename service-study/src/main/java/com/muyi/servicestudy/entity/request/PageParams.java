package com.muyi.servicestudy.entity.request;

import lombok.Data;

@Data
public class PageParams {
    public Integer page_start = 1;
    public Integer page_number = 30;
}

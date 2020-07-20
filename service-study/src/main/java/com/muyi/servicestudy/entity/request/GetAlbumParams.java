package com.muyi.servicestudy.entity.request;

import lombok.Data;

@Data
public class GetAlbumParams extends PageParams {
    private Long artist_id;
    private Integer is_online;
}

package com.muyi.servicestudy.entity.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class HystrixParams {
    @NotNull
    private String albumId;

    private String artistId;

    private String type = "cat";

    private String mobile;


}

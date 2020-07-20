package com.muyi.servicestudy.entity.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class HystrixParams {
    @NotNull
    private String albumId;

    @NotBlank
    private String artistId;

    private String type = "cat";

    @NotNull
    @Size(min = 11, max = 11, message = "mobile length error")
    private String mobile;


}

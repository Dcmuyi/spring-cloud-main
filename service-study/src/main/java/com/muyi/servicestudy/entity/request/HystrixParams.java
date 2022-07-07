package com.muyi.servicestudy.entity.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
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

    @Length(min = 10)
    private String mobile;


}

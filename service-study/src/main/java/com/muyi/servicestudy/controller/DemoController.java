package com.muyi.servicestudy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.muyi.servicestudy.entity.request.HystrixParams;
import com.muyi.servicestudy.exception.AppParamsException;
import com.muyi.servicestudy.utils.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/21.
 */
@Slf4j
@RestController
@RequestMapping("/service-study/demo")
public class DemoController {
    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;

    @HystrixCommand(fallbackMethod = "/hystrix_back", ignoreExceptions = {Exception.class})
    @GetMapping("/hystrix")
    public Result hystrix(@Validated HystrixParams hystrixParams) {
        log.info("params: " + hystrixParams);

        return Result.wrapSuccessfulResult("");
    }

    @Validated
    @PostMapping("/testPost")
    public Result testPost(@RequestBody @Valid HystrixParams hystrixParams, HttpServletRequest request) {
        log.info("params: " + hystrixParams);
        String dd = "4.12.2_2193 94";
        List<String> te = Arrays.asList(hystrixParams.getAlbumId().split(" |_"));
        log.info("====te:"+te);

        log.info("===="+ request.getHeader("token")+"===="+request.getHeader("test"));

        return Result.wrapSuccessfulResult("");
    }

    /**
     * 熔断方法
     * @param hystrixParams
     * @param bindingResult
     * @param request
     * @return
     */
    public Result hystrix_back(@Validated HystrixParams hystrixParams, BindingResult bindingResult, HttpServletRequest request) {
        log.info("call back");

        return Result.wrapErrorResult(500, "服务器开小差啦~");
    }
}

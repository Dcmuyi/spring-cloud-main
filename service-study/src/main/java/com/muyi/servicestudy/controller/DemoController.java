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
import java.util.*;
import java.util.stream.Collectors;

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

    @HystrixCommand(fallbackMethod = "/hystrix_back")
    @RequestMapping("/hystrix")
    public Result hystrix(@Validated HystrixParams hystrixParams, BindingResult bindingResult, HttpServletRequest request) {
        log.info("params: " + hystrixParams);

        try {
            Thread.sleep(5000);
        } catch (Exception e) {}

        return Result.wrapSuccessfulResult("");
    }

    @Validated
    @PostMapping("/testPost")
    public Result testPost(@RequestBody HystrixParams hystrixParams, HttpServletRequest request) {
        log.info("params: " + hystrixParams);
        String pcode = "XE110JH";
        //对应key
        String key = hystrixParams.getAlbumId()+"_"+pcode;

        //cver分割
        List<String> cverList = Arrays.asList(hystrixParams.getAlbumId().split(" |_|\\."));
        log.info("====te:"+cverList);
        LinkedHashSet keyList = new LinkedHashSet<>();
        //取前3
        cverList = cverList.stream().limit(3).collect(Collectors.toList());
        String cverString = String.join(".", cverList);
        //放入list
        keyList.add(cverString+"_"+pcode);
        keyList.add(cverString+"_");
        //前2
        cverList = cverList.stream().limit(2).collect(Collectors.toList());
        cverString = String.join(".", cverList);
        //放入list
        keyList.add(cverString+"_"+pcode);
        keyList.add(cverString+"_");

        log.info("===="+ keyList);
        log.info("----"+key);

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

package com.muyi.servicestudy.controller;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;
import com.muyi.servicestudy.utils.Result;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.muyi.servicestudy.annotation.LogAnnotation;
import org.springframework.beans.factory.annotation.Value;
import com.muyi.servicestudy.entity.request.HystrixParams;
import com.muyi.servicestudy.service.dc.DefaultServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * common request demo
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/21.
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class RequestController {
    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;

    @PostMapping("/validation")
    public void validation(@Valid HystrixParams hystrixParams) {
        log.info("params:"+ JSONObject.toJSONString(hystrixParams));
    }

    @HystrixCommand(fallbackMethod = "/hystrix_back")
    @RequestMapping("/hystrix")
    @LogAnnotation
    public Result hystrix(@Validated HystrixParams hystrixParams) {
        log.info("params: " + hystrixParams);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {}

        return Result.wrapSuccessfulResult("");
    }

    @Validated
    @PostMapping("/testPost")
    public Result testPost(@RequestBody HystrixParams hystrixParams) {
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

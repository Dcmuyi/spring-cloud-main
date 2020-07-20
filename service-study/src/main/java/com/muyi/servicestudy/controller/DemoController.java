package com.muyi.servicestudy.controller;

import com.muyi.servicestudy.entity.request.HystrixParams;
import com.muyi.servicestudy.exception.AppParamsException;
import com.muyi.servicestudy.utils.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/21.
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;

    @HystrixCommand(fallbackMethod = "/hystrix_back", ignoreExceptions = {Exception.class})
    @GetMapping("/hystrix")
    public Result hystrix(@Validated HystrixParams hystrixParams, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            String error = Optional.ofNullable(bindingResult.getFieldError())
                    .map(t->t.getField() + t.getDefaultMessage())
                    .orElse("参数异常");
            throw new AppParamsException(error);
        }

        log.info("params: " + hystrixParams);

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

package com.muyi.servicestudy.controller;

import com.muyi.servicestudy.entity.request.HystrixParams;
import com.muyi.servicestudy.service.rpc.TestService;
import com.muyi.servicestudy.utils.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/21.
 */
@Slf4j
@RestController
@RequestMapping("/service-study/feign")
public class FeignController {
    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;
    @Autowired
    private TestService testService;

    @GetMapping("/fail")
    public Result fail(String test) {
        log.info("params: " + test);

        String res = testService.fail(test);
        log.info("res:"+res);

        return Result.wrapSuccessfulResult("");
    }

    @GetMapping("/success")
    public Result success(String test) {
        log.info("params: " + test);

        String res = testService.success(test);
        log.info("res:"+res);

        return Result.wrapSuccessfulResult("");
    }
}

package com.muyi.servicehi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Muyi, dcmuyi@qq.com
 * @date 2019/11/21.
 */
@RestController
@RequestMapping("/service-hi/hello")
public class HelloController {
    @Value("${server.port}")
    String port;
    @Value("${spring.application.name}")
    String name;

    @GetMapping("/fail")
    public String fail(@RequestParam(value = "test", required = false) String test) {
        try {
            Thread.sleep(4000);
        } catch (Exception e) {}

        throw new RuntimeException("ttest");
    }

    @GetMapping("/success")
    public String success(@RequestParam(value = "test", required = false) String test) {

        return test;
    }
}

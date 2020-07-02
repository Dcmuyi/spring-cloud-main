package com.muyi.servicehi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Muyi, dcmuyi@qq.com
 * @date 2019/11/21.
 */
@RestController
@RequestMapping("/hi")
public class HelloController {
    @Value("${server.port}")

    String port;
    @Value("${spring.application.name}")
    String name;

    @RequestMapping("/hi")
    public String hi(@RequestHeader(value = "test", required = false) String test) {
        throw new RuntimeException("ttest");
//        return "hi "+name+", port: "+port+", test = "+test;
    }
}

package com.muyi.servicestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

@EnableAsync
@RestController
@SpringBootApplication
//@EnableEurekaClient
//@EnableFeignClients
@EnableHystrix
public class ServiceStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceStudyApplication.class, args);
    }

}

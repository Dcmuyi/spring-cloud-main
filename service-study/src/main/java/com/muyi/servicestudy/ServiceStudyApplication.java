package com.muyi.servicestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
public class ServiceStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceStudyApplication.class, args);
    }

}

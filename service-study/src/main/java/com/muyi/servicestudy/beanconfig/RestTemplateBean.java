package com.muyi.servicestudy.beanconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Component
public class RestTemplateBean {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

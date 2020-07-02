package com.muyi.servicestudy.helper;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

/**
 * 通用请求类
 */
@Slf4j
@Service
public class Request {
    @Autowired
    private RestTemplate restTemplate;

    public JSONObject request (String url, HashMap<String, String> params, HttpMethod method, HashMap<String, String> headers) {
        //请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/json");
        httpHeaders.add("Accept", "application/json;responseformat=3");
        httpHeaders.add("Accept-Language", "zh-CN");
        //额外header
        headers.forEach(httpHeaders::add);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        //get请求需要拼接参数
        if (HttpMethod.GET.equals(method)) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            //拼接
            params.forEach(builder::queryParam);
            //返回
            url = builder.build().encode().toString();
        }

        //超时设置
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5*1000);
        requestFactory.setReadTimeout(5*1000);
        restTemplate.setRequestFactory(requestFactory);

        //请求
        log.info("request url:"+url+"params:"+params);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, httpEntity, String.class, params);

        log.info("result: "+responseEntity);
        return JSONObject.parseObject(responseEntity.getBody());
    }
}

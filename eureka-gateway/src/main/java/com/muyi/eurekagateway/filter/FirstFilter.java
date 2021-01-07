package com.muyi.eurekagateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;


@Slf4j
@Component
public class FirstFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("first filter request ="+JSON.toJSONString(request));
        log.info("body:"+ exchange.getAttribute("POST_BODY"));
        log.info("params :"+JSONObject.toJSONString(request.getQueryParams()));

        if (!request.getHeaders().containsKey("token")) {
            throw new RuntimeException("请求参数有误");
        }

        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set("test", "bbb");
            httpHeader.set("aaaa", "bbb");
        };
        //向headers中放文件，记得build
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        //将现在的request 变成 change对象
        ServerWebExchange serverWebExchange = exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(serverWebExchange);
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
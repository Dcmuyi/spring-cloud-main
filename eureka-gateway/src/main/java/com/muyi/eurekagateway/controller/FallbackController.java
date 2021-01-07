package com.muyi.eurekagateway.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author Muyi, dcmuyi@qq.com
 * @date 2019/11/21.
 */
@Slf4j
@RestController
public class FallbackController {
    @RequestMapping("/fallback")
    public Map<String, Object> fallback(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        Throwable e = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);

        if (request instanceof AbstractServerHttpRequest) {
            AbstractServerHttpRequest req = (AbstractServerHttpRequest) request;
            HttpRequest originalReq = req.getNativeRequest();
            String uri = originalReq.getURI().toString();
            log.error("网关服务熔断；uri={}", uri, e);
        } else {
            log.error("invalid request type: {}", request.getClass().getName(), e);
        }

        Map<String, Object> res = Maps.newHashMap();
        res.put("status", 105);
        res.put("message", "网络繁忙");
        return res;
    }
}

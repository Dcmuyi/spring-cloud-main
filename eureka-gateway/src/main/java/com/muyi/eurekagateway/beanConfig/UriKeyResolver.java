//package com.muyi.eurekagateway.beanConfig;
//
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @author Muyi,  dcmuyi@qq.com
// * @date 2019-04-17.
// */
//@Component
//public class UriKeyResolver implements KeyResolver {
//    @Override
//    public Mono<String> resolve(ServerWebExchange exchange) {
//        return Mono.just(exchange.getRequest().getURI().getPath());
//    }
//}
//

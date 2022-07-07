//package com.muyi.servicestudy.service.rpc;
//
//import feign.hystrix.FallbackFactory;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * @author Muyi,  dcmuyi@qq.com
// * @date 2019/11/21.
// */
//@FeignClient(name = "service-hi", fallback = TestCallbackServiceImpl.class)
//public interface TestService {
//    @GetMapping("/service-hi/hello/fail")
//    String fail(@RequestParam(value = "test", required = false) String test);
//
//    @GetMapping("/service-hi/hello/success")
//    String success(@RequestParam(value = "test", required = false) String test);
//}

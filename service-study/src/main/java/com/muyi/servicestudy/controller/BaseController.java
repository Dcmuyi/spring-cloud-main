package com.muyi.servicestudy.controller;

import com.alibaba.fastjson.JSONObject;
import com.muyi.servicestudy.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class BaseController<T extends BaseService> {
//    @Autowired
//    private T baseService;
//
//    @GetMapping("/list")
//    public void getList(HttpServletRequest request) {
//        log.info("get list params"+ JSONObject.toJSONString(request.getParameterMap()));
//        baseService.getList(request);
//    }

}

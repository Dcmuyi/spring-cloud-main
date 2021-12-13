package com.muyi.servicestudy;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Muyi
 * @date 2019-04-17.
 */
@Component
@Order(10)
@Slf4j
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==arge==："+ JSONObject.toJSONString(args));
    }
}

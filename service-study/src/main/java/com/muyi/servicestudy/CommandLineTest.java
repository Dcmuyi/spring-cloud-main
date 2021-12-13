package com.muyi.servicestudy;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Muyi
 * @date 2019-04-17.
 */
@Component
@Order(11)
@Slf4j
public class CommandLineTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("==command==+"+ JSONObject.toJSONString(args));
    }
}

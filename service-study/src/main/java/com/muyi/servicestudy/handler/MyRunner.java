package com.muyi.servicestudy.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 自定义启动类，项目启动后执行
 * @date 2019-04-17.
 */
@Slf4j
@Component
public class MyRunner implements CommandLineRunner {
    @Override
    public void run(String... arg) {
        log.info("=============================start===========");
    }
}

package com.muyi.servicestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author muyi
 * Created at 2022/6/30
 */
@Slf4j
@Service
public class AsyncService {
    @Async(value = "threadPool1")
    public void asyncMethod() {
        try {
            log.info(Thread.currentThread().getName()+"===begin");
            Thread.sleep(3000);
            log.info(Thread.currentThread().getName()+"===end");
        } catch (Exception e) {}
    }

    @Async(value = "threadPool2")
    public Future<String> asyncMethod2() {
        try {
            log.info(Thread.currentThread().getName()+"===begin2");
            Thread.sleep(3000);
            log.info(Thread.currentThread().getName()+"===end2");
        } catch (Exception e) {}

        return new AsyncResult<>(Thread.currentThread().getName()+"===");
    }
}

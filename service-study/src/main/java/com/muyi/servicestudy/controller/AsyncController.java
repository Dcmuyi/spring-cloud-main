package com.muyi.servicestudy.controller;

import com.muyi.servicestudy.service.AsyncService;
import com.muyi.servicestudy.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author muyi
 * Created at 2022/6/30
 */
@Slf4j
@RestController("/async/")
public class AsyncController {
    @Autowired
    private AsyncService asyncService;
    //异步任务Async注解调试
    @GetMapping("test")
    public Result<String> testAsync() throws ExecutionException, InterruptedException {
        log.info("=====");
        asyncService.asyncMethod();
        asyncService.asyncMethod();
        asyncService.asyncMethod();
        asyncService.asyncMethod();
        Future<String> f1 = asyncService.asyncMethod2();
        Future<String> f2 = asyncService.asyncMethod2();
        Future<String> f3 = asyncService.asyncMethod2();
        log.info("+++++");
        log.info(f1.get());
        log.info(f3.get());

        return Result.wrapSuccessfulResult("ddd");
    }

    @GetMapping("batch")
    public void testBatch() throws ExecutionException, InterruptedException {
        //创建list存放future
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i<10;i++) {
            futures.add(asyncService.asyncMethod2());
        }

        //循环get
        for (Future<String> future : futures) {
            log.info("get async:"+future.get());
        }
    }
}

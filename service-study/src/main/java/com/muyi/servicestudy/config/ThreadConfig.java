package com.muyi.servicestudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.*;

/**
 * 生产线程池
 */
@Configuration
public class ThreadConfig {
    @Bean(name = "threadPool1")
    public Executor executor1() {
        return new ThreadPoolExecutor(2,3,
                20, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1), new MyThreadFactory("muyi1"), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean(name = "threadPool1")
    public Executor executor2() {
        return new ThreadPoolExecutor(2,2,
                20, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(1), new MyThreadFactory("muyi2"), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}

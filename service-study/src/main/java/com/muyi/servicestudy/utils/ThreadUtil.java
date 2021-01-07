package com.muyi.servicestudy.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Muyi, dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Data
@Component
public class ThreadUtil {
    private  int corePoolSize = 2;
    private  int maximumPoolSize = 3;
    private  long keepAliveTime = 60;
    private static ThreadPoolExecutor executorService = null;

    public  ThreadPoolExecutor getExecutorService() {
        if (null == executorService){
            executorService = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(100),new ThreadPoolExecutor.CallerRunsPolicy());
        }

        return executorService;
    }
}

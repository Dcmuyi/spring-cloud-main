package com.muyi.code.juc;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
//多线程注解
@Async
public class FutureTest {
    public static void main(String[] args) throws Exception{

        List<FutureTask<String>> list = new ArrayList<>();
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2,4,20, TimeUnit.MINUTES,new LinkedBlockingQueue<>(4), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i=0;i<10;i++) {
            int finalI = i;
            FutureTask<String> futureTask = new FutureTask<>(()-> {
                try {
                    Thread.sleep(1000);
                } catch (Exception ignore){}

                return "dd"+ finalI;
            });

            executorService.execute(futureTask);
            list.add(futureTask);
        }

        List<String> re = new ArrayList<>();
        for (FutureTask<String> f : list) {
            log.info(f.toString());
            re.add(f.get());
        }

        log.info("==:"+JSONObject.toJSONString(re));

    }

    public static void test1() throws InterruptedException {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2,4,20, TimeUnit.MINUTES,new LinkedBlockingQueue<>(4), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i =0; i<7;i++) {
            int finalI = i;
            executorService.execute(() -> {
                log.info(Thread.currentThread().getName()+" begin "+ finalI);
                try {
                    Thread.sleep(5000);
                } catch (Exception ignore) {}
                log.info(Thread.currentThread().getName()+" end "+ finalI);
            });
//
//            System.out.println("=====================================thread-pool-info:=====================================");
//            System.out.println("CorePoolSize:" + executorService.getCorePoolSize());
//            System.out.println("PoolSize:" + executorService.getPoolSize());
//            System.out.println("ActiveCount:" + executorService.getActiveCount());
//            System.out.println("KeepAliveTime:" + executorService.getKeepAliveTime(TimeUnit.SECONDS));
//            System.out.println("QueueSize:" + executorService.getQueue().size());
        }

        Thread.currentThread().join();
    }

    public static void testS() throws ExecutionException, InterruptedException {
        AtomicLong a = new AtomicLong(92);
        String b = "bbb";

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,3,20, TimeUnit.MINUTES,new LinkedBlockingQueue<>(8), new ThreadPoolExecutor.CallerRunsPolicy());

        log.info("======");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info(Thread.currentThread().getName()+"==begin===");
            try {
                TimeUnit.MICROSECONDS.sleep(1200);
            } catch (InterruptedException e) {
                log.info("thread error");
                e.printStackTrace();
            }

            return Thread.currentThread().getName()+"end";
//            throw new RuntimeException("end");
        }).handleAsync((re, ex) -> {
            if (null != ex) {
                log.info("handle ex");
                return ex.getMessage();
            } else {
                return re;
            }
        }).thenApplyAsync((re) -> {
            log.info("then:"+re);
            return re;
        });


        future.whenComplete((re, ex) -> {
            if (ex == null) {
                log.info(Thread.currentThread().getName() + re);
            } else {
                log.info(Thread.currentThread().getName() + "error");
                ex.printStackTrace();
            }
        });

        log.info("sl");
        TimeUnit.MICROSECONDS.sleep(1000);

        log.info("eep");

        log.info(Thread.currentThread().getName()+"===");
        Thread.currentThread().join();

    }

    public static void test() throws Exception{
        String a = "aaa";
        String b = "bbb";

        FutureTask<Student> futureTask = new FutureTask(() -> demo1(a));

        FutureTask<Student> futureTask1 = new FutureTask(() -> demo2(b));

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,3,20, TimeUnit.MINUTES,new LinkedBlockingQueue<>());
        threadPoolExecutor.execute(futureTask);
        threadPoolExecutor.execute(futureTask1);
        log.info("===is done==="+futureTask.isDone());

        Object stu1 = futureTask.get();
        log.info(JSONObject.toJSONString(stu1));
        log.info("线程1执行结束");
//        Student stu2 = futureTask1.get();
//        log.info(JSONObject.toJSONString(stu2));
        log.info("线程2执行结束");
        log.info("主线程执行结束====");

    }

    private static Student demo1(String a) {
        log.info("===begin a===");
        try {
            Thread.sleep(1000);
        } catch (Exception ignore) {}

        Student student1 = new Student();
        student1.setName(a);
        log.info("===end a===");

        throw new RuntimeException("error");
//        return student1;
    }
    private static Student demo2(String b) throws Exception{
        log.info("线程b开始执行======");
        Thread.sleep(1000);
        Student student1 = new Student();
        student1.setName(b);
        int a = new Random().nextInt(10);
        log.info("="+a+"=");
        if (a > 5) {
            throw new Exception("====");
        }
        log.info("线程b执行结束======");
        return student1;
    }
}

class Student{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
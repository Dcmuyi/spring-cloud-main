package com.muyi.servicestudy;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.FutureTask;

@Slf4j
public class FutureTest {
    public static void main(String[] args) throws Exception{
        test();
    }

    public static void test() throws Exception{
        String a = "aaa";
        String b = "bbb";

        FutureTask futureTask = new FutureTask(() -> demo1(a));

        FutureTask<Student> futureTask1 = new FutureTask(() -> {
//            throw new Exception("====");
            return demo2(b);
        });

        new Thread(futureTask).start();
        new Thread(futureTask1).start();

        Object stu1 = futureTask.get();
        if (futureTask1.isDone()) {

        }
        log.info(JSONObject.toJSONString(stu1));
        log.info("线程1执行结束");
        Student stu2 = futureTask1.get();
        log.info(JSONObject.toJSONString(stu2));
        log.info("线程2执行结束");
        log.info("主线程执行结束====");

    }

    private static Student demo1(String a) throws Exception{
        log.info("===begin a===");
        Thread.sleep(5000);
        Student student1 = new Student();
        student1.setName(a);
        log.info("===end a===");
        return student1;
    }
    private static Student demo2(String b) throws Exception{
        log.info("线程b开始执行======");
        Thread.sleep(1000);
        Student student1 = new Student();
        student1.setName(b);
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
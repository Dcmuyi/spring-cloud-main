package com.muyi.servicestudy.service.design;

import lombok.Data;

//懒汉+锁+volatile内存可见
public class Single {
    private static volatile Single instance;
    private Single() {

    }

    public static synchronized Single getInstance() {
        if (instance == null) {
            instance = new Single();
        } else {
            System.out.println("已创建");
        }

        return instance;
    }
}

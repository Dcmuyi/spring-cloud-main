package com.muyi.code.design;

//饿汉+直接创建对象
public class SingleTwo {
    private static SingleTwo instance = new SingleTwo();
    private SingleTwo() {}

    public static SingleTwo getInstance() {
        return instance;
    }
}

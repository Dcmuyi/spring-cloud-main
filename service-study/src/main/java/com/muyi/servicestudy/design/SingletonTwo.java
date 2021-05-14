package com.muyi.servicestudy.design;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 单例模式-饿汉
 * @date 2019-04-17.
 */
public class SingletonTwo {
    private static SingletonTwo instance = new SingletonTwo();

    private SingletonTwo() {

    }

    public static SingletonTwo getInstance() {
        return instance;
    }
}

package com.muyi.servicestudy.design;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 单例模式-内部类
 * @date 2019-04-17.
 */
public class SingletonThree {
    private static class SingletonHolder {
        private static final SingletonThree INSTANCE = new SingletonThree();
    }

    private SingletonThree() {

    }

    public static SingletonThree getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

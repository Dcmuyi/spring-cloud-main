package com.muyi.servicestudy.design;


/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 单例模式-懒汉
 * @date 2019-04-17.
 */
public class Singleton {
    private static Singleton instance = null;

    private Singleton() {

    }

    public static Singleton getInstance() throws RuntimeException{
        if (null == instance) {
            synchronized (Singleton.class) {
                if (null == instance) {
                    instance = new Singleton();
                }
            }
        }

        return instance;
    }
}

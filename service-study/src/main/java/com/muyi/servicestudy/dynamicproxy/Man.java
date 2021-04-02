package com.muyi.servicestudy.dynamicproxy;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 被代理类
 * @date 2019-04-17.
 */
public class Man implements People{
    @Override
    public String say() {
        return "i am man";
    }

    @Override
    public String eat() {
        return "i eat food";
    }
}

package com.muyi.servicestudy;

import com.muyi.servicestudy.dynamicproxy.InvocationHandlerImpl;
import com.muyi.servicestudy.dynamicproxy.Man;
import com.muyi.servicestudy.dynamicproxy.People;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 动态代理启动类
 * @date 2019-04-17.
 */
@Slf4j
public class DynamicProxyDemonstration {
    public static void main(String[] args) {
        //真实的代理对象
        Man man = new Man();

        //代理
        InvocationHandler handler = new InvocationHandlerImpl(man);

        ClassLoader classLoader = man.getClass().getClassLoader();
        Class[] classes = man.getClass().getInterfaces();

        //通过指定类装载器、一组接口及调用处理器生成动态代理类实例
        People people = (People)Proxy.newProxyInstance(classLoader, classes, handler);

        log.info("代理类对象:"+people.getClass().getName());
        String say = people.say();
        log.info("===="+say);

    }
}

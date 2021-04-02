package com.muyi.servicestudy.dynamicproxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Muyi, dcmuyi@qq.com
 * @desc 被代理类
 * @date 2019-04-17.
 */
@Slf4j
public class InvocationHandlerImpl implements InvocationHandler {
    private Object object;

    public InvocationHandlerImpl(Object object) {
        this.object=object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("===before invoke===");
        log.info("===method:"+method+"===");

        Object res = method.invoke(object, args);

        log.info("===after invoke===");
        return res;
    }
}

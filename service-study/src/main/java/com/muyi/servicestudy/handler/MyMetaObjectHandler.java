package com.muyi.servicestudy.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.muyi.servicestudy.utils.DateUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        long nowTime = DateUtil.getCurrentTime();
        metaObject.setValue("createdAt", nowTime);
        metaObject.setValue("updatedAt", nowTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        long nowTime = DateUtil.getCurrentTime();
        this.setFieldValByName("updatedAt", nowTime, metaObject);
        System.out.println("====");
    }
}
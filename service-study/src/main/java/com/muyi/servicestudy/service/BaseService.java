package com.muyi.servicestudy.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muyi.servicestudy.annotation.QueryField;
import com.muyi.servicestudy.entity.response.PageData;
import com.muyi.servicestudy.exception.AppParamsException;
import com.muyi.servicestudy.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

@Slf4j
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 通用查询方法，通过注解和传参自动生成查询条件
     * @param clazz 表结构实体类
     * @param request 请求体
     * @return
     */
    public PageData<T> getList(Class<T> clazz, HttpServletRequest request) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();

        //根据注解生成查询语句
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(QueryField.class);
            //注解设置了可查询，判断参数和是否必传
            if (annotation != null) {
                //获取自定义注解属性
                JSONObject annotationObj = JSONObject.parseObject(JSONObject.toJSONString(annotation));
                String fieldName = Utils.getStringByObject(annotationObj, "name");
                String queryStr = request.getParameter(fieldName);
                //判断必传
                if (Utils.getStringByObject(annotationObj, "must").equals("true") && StringUtils.isEmpty(queryStr)) {
                    throw new AppParamsException(fieldName + "can not be null");
                }

                //有传参->查询
                if (!StringUtils.isEmpty(queryStr)) {
                    //类型转换，防止mysql强制转换
                    Class fieldType = field.getType();
                    if (fieldType.equals(Integer.class) || fieldType.equals(Long.class)) {
                        queryWrapper.eq(fieldName, Long.parseLong(queryStr));
                    } else {
                        queryWrapper.eq(fieldName, queryStr);
                    }
                }
            }
        }

        log.info("query wrapper : "+queryWrapper);

        //分页
        Integer pageStart = Optional.of(request).map(t->t.getParameter("page_start")).map(Integer::parseInt).orElse(1);
        Integer pageNum =  Optional.of(request).map(t->t.getParameter("page_number")).map(Integer::parseInt).orElse(30);
        IPage<T> iPage = new Page<>(pageStart, pageNum);
        log.info("ipage : "+iPage);

        IPage<T> result = baseMapper.selectPage(iPage, queryWrapper);
        log.info("result : "+ JSONObject.toJSONString(result));

        return PageData.fillPage(result);
    }
}

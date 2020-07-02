package com.muyi.servicestudy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @dep 云端技术部
 * @desc 时间处理类
 * @company 浙江亿咖通科技有限公司  Zhejiang e-CarX Technology Co., Ltd
 * @address 浙江省杭州市滨江区泰安路杭州印B座17楼(华联.星光大道2期西)
 * @date 2019-04-17.
 */
public class DateUtil {
    /**
     * 获取当前时间戳
     * @return
     */
    public static Long getCurrentTime() {
        long times = System.currentTimeMillis();

        return times/1000;
    }

    /**
     * 获取当前时间毫秒戳
     * @return
     */
    public static Long getCurrentMilliTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期
     * @param milliTime
     * @return
     */
    public static String getDate(Long milliTime, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(milliTime);

        return dateFormat.format(date);
    }

    /**
     * 把日期转为时间戳
     * @param date
     * @return
     */
    public static Long dateToStamp(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        long stamp = 0;
        try {
            Date d = simpleDateFormat.parse(date);
            stamp = d.getTime();
        } catch (ParseException ignore) {}

        return stamp;
    }
}

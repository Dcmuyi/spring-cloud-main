package com.muyi.servicestudy.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description： 通用帮助类, 简化代码
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/21.
 */
@Slf4j
public class Utils {
    /**
     * 根据地址下载文件到本地
     * @param url
     * @param path
     * @param name
     */
    public static void downloadFile(String url, String path, String name) {
        try {
            URL urlNew = new URL(url);
            InputStream imgStream = urlNew.openStream();
            File filepath = new File(path);
            if (!filepath.exists()) {
                filepath.mkdirs();
            }

            OutputStream os = new FileOutputStream(new File(filepath,name));
            int len;
            byte[] bys = new byte[1024];
            while ((len=imgStream.read(bys))!=-1){
                os.write(bys,0,len);
            }
            os.close();
            imgStream.close();
        } catch (Exception e) {
            log.info("download img error: "+path+"/"+name);
        }
    }

    /**
     * md5 32位加密
     * @param str
     * @return
     */
    public static String encodeByMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes("UTF-8"));
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }

    private static String fillMD5(String md5){
        //如果不够32位则回调自身补零，最后返回32位长度的签名
        return md5.length()==32?md5:fillMD5("0"+md5);
    }

    /**
     * 把时分秒转为秒
     * @param formatTime
     * @return
     */
    public static Long getSecondsByString(String formatTime) {
        Long seconds = 0L;
        if ("".equals(formatTime)) {
            return seconds;
        }

        List<String> timeList = Arrays.asList(formatTime.split(":"));
        Long hour, minute, second;
        switch (timeList.size()) {
            case 1:
                seconds = Long.valueOf(timeList.get(0));
                break;
            case 2:
                minute = Long.valueOf(timeList.get(0));
                second = Long.valueOf(timeList.get(1));

                seconds = minute * 60 + second;
                break;
            case 3:
                hour = Long.valueOf(timeList.get(0));
                minute = Long.valueOf(timeList.get(1));
                second = Long.valueOf(timeList.get(2));

                seconds = hour * 3600 + minute * 60 + second;
                break;
            default:
                seconds = 0L;
        }

        return seconds;
    }

    /**
     * optional方式获取long
     * @param obj
     * @param key
     * @return
     */
    public static Long getLongByObject(JSONObject obj, String key) {
        return Optional.ofNullable(obj).map(t->t.getLong(key)).orElse(0L);
    }

    /**
     * optional方式获取string
     * @param obj
     * @param key
     * @return
     */
    public static String getStringByObject(JSONObject obj, String key) {
        return Optional.ofNullable(obj).map(t->t.getString(key)).orElse("");
    }

    /**
     * optional方式获取int
     * @param obj
     * @param key
     * @return
     */
    public static Integer getIntegerByObject(JSONObject obj, String key) {
        return Optional.ofNullable(obj).map(t->t.getInteger(key)).orElse(0);
    }
}

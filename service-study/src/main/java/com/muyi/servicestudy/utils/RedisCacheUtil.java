package com.muyi.servicestudy.utils;

import java.util.Map;
import java.util.Collections;
import java.lang.reflect.Method;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.connection.RedisConnection;

/**
 * @author Muyi,  dcmuyi@qq.com
 * redis常用方法
 */
@Component
public class RedisCacheUtil {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RedisCacheUtil.class);

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final long DEFAULT_EXPIRE_SECONDS = 600;
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setString(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * check cache
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * delete cache
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * scan
     */
    public Cursor<Map.Entry<Object, Object>> scan(String pattern, int limit) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(limit).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        return (Cursor) redisTemplate.executeWithStickyConnection(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize);
            }
        });
    }

    /**
     * 分布式锁
     * @param key
     * @param val
     * @param milliSeconds
     * @return
     */
    public Boolean tryLock(String key, String val, long milliSeconds) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();

            String result = jedis.set(key, val, "nx", "ex", milliSeconds);
            if (LOCK_SUCCESS.equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    /**
     * 解锁
     * @param key
     * @param val
     * @return
     */
    public Boolean releaseLock(String key, String val) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(key),
                    Collections.singletonList(val));
            if (RELEASE_SUCCESS.equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    /**
     * 获取key的剩余有效时间
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public Long getTTL(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    public String getCacheData(String key) {
        return getCacheData(key, null, null, DEFAULT_EXPIRE_SECONDS);
    }

    public String getCacheData(String key, long seconds) {
        return getCacheData(key, null, null, seconds);
    }

    public String getCacheData(String key, Class<?> targetClass, String methodName) {
        return getCacheData(key, targetClass, methodName, DEFAULT_EXPIRE_SECONDS);
    }

    /**
     * 获取缓存，如果不存在则通过反射获取数据并缓存
     * @param key
     * @param targetClass
     * @param methodName
     * @return
     */
    public String getCacheData(String key, Class<?> targetClass, String methodName, long seconds) {
        String res = redisTemplate.opsForValue().get(key);
        if (null == res && null != targetClass && null != methodName) {
            try {
                Object object = SpringContextUtil.getBean(targetClass);

                Method method = targetClass.getDeclaredMethod(methodName, String.class);
                Object data = method.invoke(object, key);

                res = data.toString();

                redisTemplate.opsForValue().set(key, res, seconds, TimeUnit.SECONDS);
            } catch (Exception e) {
                LOG.error("get cache error :"+e.toString());
            }
        }

        return res;
    }
}
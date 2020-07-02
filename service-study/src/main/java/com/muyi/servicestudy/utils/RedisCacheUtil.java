package com.muyi.servicestudy.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis常用方法
 */
@Slf4j
@Component
public class RedisCacheUtil {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
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
     * 本地调用，存储Hash类型数据到缓存服务器，设置有效期
     */
    public void setHashString(String key, Map<String, String> fieldMap, long timeout, TimeUnit unit) {
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForHash().putAll(key, fieldMap);
                operations.expire(key, timeout, unit);
                Object val = operations.exec();
                return val;
            }
        };
        redisTemplate.execute(sessionCallback);
    }

    /**
     * 本地调用，存储hash类型数据到服务器，在此前转换String，设置有效期
     */
    public void setHashObject(String key, Map objectMap, long timeout, TimeUnit unit) {
        Map<String, String> fieldMap = new HashMap<>(16);
        objectMap.forEach((k,v)->{
            fieldMap.put(k + "", v + "");
        });
        setHashString(key,fieldMap,timeout,unit);
    }


    /**
     * 本地调用，存储Hash类型数据到缓存服务器，设置有效期
     */
    public void setHashString(String key, Map<String, Object> fieldMap) {
        redisTemplate.opsForHash().putAll(key, fieldMap);
    }

    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 本地调用，存储Hash类型部分数据到缓存服务器，设置有效期
     */
    public void setHashPartString(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 本地调用，根据key查询Hash类型数据,因为使用的是StringTemplate，都转成String
     */
    public Map<String, String> getHashString(String key) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        Map<String, String> strMap = new HashMap<>(16);
        map.forEach((k, v) -> {
            strMap.put(k + "", v + "");
        });
        return strMap;
    }

    /**
     * 本地调用，从缓存服务器删除数据
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 本地调用，校验数据是否在缓存服务器上已存在
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

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

}
package com.muyi.servicestudy.beanconfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Configuration
@EnableTransactionManagement
public class RedisBean {

    /**
     * redis服务地址
     */
    @Value("${redis.host}")
    private String redisHost;

    /**
     * redis端口号
     */
    @Value("${redis.port}")
    private int redisPort;

    /**
     * redis密码
     */
    @Value("${redis.password:}")
    private String redisPassword;

    /**
     * redis数据库序号
     */
    @Value("${redis.database}")
    private int redisDatabase;

    /**
     * 当客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能
     */
    @Value("${redis.timeout:100}")
    private int redisTimeOut ;

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
     */
    @Value("${redis.pool.max-wait:-1}")
    private int redisPoolMaxWait ;

    /**
     * 可用连接实例的最大数目，默认值为8；
     * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    @Value("${redis.pool.max-active:10}")
    private int redisPoolMaxActive ;

    /**
     * 控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值也是8
     */
    @Value("${redis.pool.min-idle:10}")
    private int redisPoolMinIdle ;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
     */
    @Value("${redis.pool.max-idle:10}")
    private int redisPoolMaxIdle ;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * RedisTemplate配置
     *
     * @param
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate<String,String> getStringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(getJedisConnectionFactory());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * redis连接工厂
     *
     * @return
     */
    @Bean
    @Primary
    public JedisConnectionFactory getJedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setDatabase(redisDatabase);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));


        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder = (
                JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxWaitMillis(redisPoolMaxWait);
        genericObjectPoolConfig.setMaxIdle(redisPoolMaxIdle);
        genericObjectPoolConfig.setMaxTotal(redisPoolMaxActive);
        genericObjectPoolConfig.setMinIdle(redisPoolMinIdle);
        JedisClientConfiguration jedisClientConfiguration = jedisPoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig).build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }
}


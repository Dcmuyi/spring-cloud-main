package com.muyi.servicestudy.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 数据库配置
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/5.
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DataSourceConfig {
    /**
     * 根据url自动识别
     这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     监控统计用的filter:stat 日志用的filter:log4j 防御sql注入的filter:wall
     */
    @Value("${spring.datasource.filters}")
    private String filters;
    /**
     * 最大连接池数量
     */
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    @Value("${spring.datasource.maxWait}")
    private long maxWait;
    /**
     * 最小连接池数量
     */
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    /**
     * 连接保持空闲而不被驱逐的最长时间
     */
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用
     */
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    /**
     * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
     */
    @Value("${spring.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    @Value("${spring.datasource.testOnBorrow}")
    private Boolean testOnBorrow;
    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    @Value("${spring.datasource.testOnReturn}")
    private Boolean testOnReturn;
    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
     */
    @Value("${spring.datasource.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    @Value("${spring.datasource.maxOpenPreparedStatements}")
    private int maxOpenPreparedStatements;

    /**
     * 数据库连接
     */
    @Value("${spring.datasource.url.muyi}")
    private String urlMuyi;
    @Value("${spring.datasource.username.muyi}")
    private String usernameMuyi;
    @Value("${spring.datasource.password.muyi}")
    private String passwordMuyi;

    @Value("${spring.datasource.url.dc}")
    private String urlDc;
    @Value("${spring.datasource.username.dc}")
    private String usernameDc;
    @Value("${spring.datasource.password.dc}")
    private String passwordDc;
    /**
     * 每隔多少分钟把监控数据输出到日志中  单位为毫秒
     */
    @Value("${spring.datasource.timeBetweenLogStatsMillis}")
    private long timeBetweenLogStatsMillis;
}

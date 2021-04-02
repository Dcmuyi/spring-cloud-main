package com.muyi.servicestudy.beanconfig;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallFilter;
import com.muyi.servicestudy.config.DataSourceConfig;
import com.muyi.servicestudy.config.DruidStatConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019/11/5.
 */
public class DataSourceBeanCommon {
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private DruidStatConfig druidStatConfig;

    /**
     * 获取通用数据库配置
     * @return
     */
    public DruidDataSource getCommonDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceConfig.getDriverClass());
        dataSource.setMaxActive(dataSourceConfig.getMaxActive());
        dataSource.setInitialSize(dataSourceConfig.getInitialSize());
        dataSource.setMaxWait(dataSourceConfig.getMaxWait());
        dataSource.setMinIdle(dataSourceConfig.getMinIdle());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(dataSourceConfig.getValidationQuery());
        dataSource.setTestWhileIdle(dataSourceConfig.getTestWhileIdle());
        dataSource.setTestOnBorrow(dataSourceConfig.getTestOnBorrow());
        dataSource.setTestOnReturn(dataSourceConfig.getTestOnReturn());
        dataSource.setPoolPreparedStatements(dataSourceConfig.getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());
        dataSource.setTimeBetweenLogStatsMillis(dataSourceConfig.getTimeBetweenLogStatsMillis());
        List<Filter> filters = new ArrayList<>();
        StatFilter statFilter = new StatFilter();
        statFilter.setDbType(druidStatConfig.getDbType());
        statFilter.setLogSlowSql(druidStatConfig.isLogSlowSql());
        statFilter.setSlowSqlMillis(druidStatConfig.getSlowSqlMillis());
        statFilter.setMergeSql(druidStatConfig.isMergeSql());
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setConnectionLoggerName(druidStatConfig.getConnectionLoggerName());
        slf4jLogFilter.setDataSourceLoggerName(druidStatConfig.getDataSourceLoggerName());
        slf4jLogFilter.setResultSetLoggerName(druidStatConfig.getResultSetLoggerName());
        slf4jLogFilter.setStatementLoggerName(druidStatConfig.getStatementLoggerName());
        slf4jLogFilter.setStatementLogEnabled(druidStatConfig.getStatementExecutableSqlLogEnable());
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType(druidStatConfig.getDbType());
        wallFilter.setLogViolation(druidStatConfig.getLogViolation());
        wallFilter.setThrowException(druidStatConfig.getThrowException());
        filters.add(statFilter);
        filters.add(slf4jLogFilter);
        filters.add(wallFilter);
        dataSource.setProxyFilters(filters);
        return dataSource;
    }
}

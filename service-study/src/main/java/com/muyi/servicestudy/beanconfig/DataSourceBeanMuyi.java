package com.muyi.servicestudy.beanconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.muyi.servicestudy.config.DataSourceConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Configuration
@MapperScan(basePackages = "com.muyi.servicestudy.mapper.muyi", sqlSessionFactoryRef = "muyiSqlSessionFactory")
public class DataSourceBeanMuyi extends DataSourceBeanCommon {
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Bean(name = "muyiTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "muyiSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setPlugins(new Interceptor[]{
                new PaginationInterceptor(),
                new PerformanceInterceptor(),
                new OptimisticLockerInterceptor()
        });
        return sessionFactory.getObject();
    }

    @Bean(name = "muyiDataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = super.getCommonDruidDataSource();
        dataSource.setUrl(dataSourceConfig.getUrlMuyi());
        dataSource.setUsername(dataSourceConfig.getUsernameMuyi());
        dataSource.setPassword(dataSourceConfig.getPasswordMuyi());
        return dataSource;
    }
}

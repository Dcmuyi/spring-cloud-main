package com.muyi.servicestudy.beanconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.muyi.servicestudy.handler.MyMetaObjectHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Configuration
@MapperScan(basePackages = "com.muyi.servicestudy.mapper.dc", sqlSessionFactoryRef = "dcSqlSessionFactory")
public class DataSourceBeanDc {
    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        conf.setMetaObjectHandler(new MyMetaObjectHandler());
        return conf;
    }

    @Bean(name = "dcDataSource")
    @ConfigurationProperties(prefix = "datasource.dc")
    public DruidDataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dcSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dcDataSource") DataSource dataSource, @Qualifier("globalConfiguration") GlobalConfig globalConfig) throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPlugins(new Interceptor[]{
                new PaginationInterceptor(),
                //去掉性能分析器可隐藏控制台打印sql
                new PerformanceInterceptor(),
                new OptimisticLockerInterceptor()
        });
        sessionFactory.setGlobalConfig(globalConfig);
        //配置mybatis自动转驼峰不生效
        //sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(false);

        //设置空值可以执行
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }

}

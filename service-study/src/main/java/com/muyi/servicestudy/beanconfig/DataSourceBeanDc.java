package com.muyi.servicestudy.beanconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.muyi.servicestudy.config.DataSourceConfig;
import com.muyi.servicestudy.config.DruidStatConfig;
import com.muyi.servicestudy.handler.MyMetaObjectHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Configuration
@MapperScan(basePackages = "com.muyi.servicestudy.mapper.dc", sqlSessionFactoryRef = "dcSqlSessionFactory")
public class DataSourceBeanDc extends DataSourceBeanCommon {
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private DruidStatConfig druidStatConfig;

    @Bean(name = "dcTransactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "dcSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sessionFactory.setConfiguration(configuration);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources(MAPPER_LOCATION));
        sessionFactory.setPlugins(new Interceptor[]{
                new PaginationInterceptor(),
                new PerformanceInterceptor(),
                new OptimisticLockerInterceptor()
        });
        sessionFactory.setGlobalConfig(globalConfiguration());
        return sessionFactory.getObject();
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),druidStatConfig.getUrlPattern());
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", druidStatConfig.getAllow());
        // IP黑名单(共同存在时，deny优先于allow)
//        servletRegistrationBean.addInitParameter("deny", "192.168.1.100");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", druidStatConfig.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", druidStatConfig.getLoginPassword());
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", druidStatConfig.getResetEnable());

        return servletRegistrationBean;
    }

    @Bean
    public GlobalConfig globalConfiguration() {
        GlobalConfig conf = new GlobalConfig();
        conf.setMetaObjectHandler(new MyMetaObjectHandler());
        return conf;
    }

    @Bean(name = "dcDataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = super.getCommonDruidDataSource();
        dataSource.setUrl(dataSourceConfig.getUrlDc());
        dataSource.setUsername(dataSourceConfig.getUsernameDc());
        dataSource.setPassword(dataSourceConfig.getPasswordDc());

        return dataSource;
    }
}

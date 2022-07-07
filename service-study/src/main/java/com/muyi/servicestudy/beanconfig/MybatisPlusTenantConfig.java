//package com.muyi.servicestudy.beanconfig;
//
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.LongValue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
///**
// * mybatis-plus多租户插件
// * @author Muyi,  dcmuyi@qq.com
// * @since 2021-02-01
// */
//@Configuration
//public class MybatisPlusConfig {
//    /**
//     * 设置忽略的table list
//     */
//    private static List<String> IGNORE_TABLE_LIST = Collections.singletonList("bm_music");
//
//    /**
//     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
//     */
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
//            @Override
//            public Expression getTenantId() {
//                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//                long kind = Optional.ofNullable(request.getHeader("kind")).map(Long::parseLong).orElse(0L);
//
//                return new LongValue(kind);
//            }
//
//            @Override
//            public String getTenantIdColumn() {
//                // 对应数据库租户ID的列名
//                return "kind";
//            }
//
//            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
//            @Override
//            public boolean ignoreTable(String tableName) {
//                return IGNORE_TABLE_LIST.stream().anyMatch(x->x.contains(tableName.toLowerCase()));
//            }
//        }));
//        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
//        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        return interceptor;
//    }
//
////    @Bean
////    public ConfigurationCustomizer configurationCustomizer() {
////        return configuration -> configuration.setUseDeprecatedExecutor(false);
////    }
//}

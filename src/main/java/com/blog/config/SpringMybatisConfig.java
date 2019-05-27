package com.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * Mybatis 配置
 *
 * @author wang
 */
@Order(1)
@ComponentScan(basePackages = "com.blog")
@Configuration
@PropertySource(value = "classpath:dataSource.properties")
@MapperScan(basePackages = "com.blog.web.dao")
public class SpringMybatisConfig {

    @Value("${jdbc_driverClassName}")
    private static String jdbcDriver;
    @Value("${jdbc_url}")
    private static String jdbcUrl;
    @Value("${jdbc_userName}")
    private static String jdbcUserName;
    @Value("${jdbc_password}")
    private static String jdbcPassword;
    /**
     * 初始化大小
     * ，最小，最大
     */

    @Value("${jdbc_initialSize}")
    private static int initialSize;

    @Value("${jdbc_minIdle}")
    private static int minIdle;

    @Value("${jdbc_maxActive}")
    public static int maxActive;
    /**
     * 配置获取连接等待超时的时间
     */
    @Value("${jdbc_maxWait}")
    private static long maxWait;
    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${jdbc_timeBetweenEvictionRunsMillis}")
    private static long timeBetweenEvictionRunsMillis;
    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("${jdbc_minEvictableIdleTimeMillis}")
    private static long minEvictableIdleTimeMillis;

    @Value("${jdbc_typeAliasesPackage}")
    private static String typeAliasesPackage;

    @Value("${mybatis.mapperLocations}")
    private static String mapperLocations;

    @Bean("dataSource")
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(jdbcDriver);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(jdbcUserName);
        druidDataSource.setPassword(jdbcPassword);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return druidDataSource;

    }

    @Bean
    public SqlSessionFactoryBean sessionFactory(@Qualifier(value = "dataSource") DruidDataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //起别名
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

        //设置mapperLocations
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = patternResolver.getResources(mapperLocations);
            sqlSessionFactoryBean.setMapperLocations(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置分页插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{this.pageInterceptor()});
        return sqlSessionFactoryBean;
    }


    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }


}

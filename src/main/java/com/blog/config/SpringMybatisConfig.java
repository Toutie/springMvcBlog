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
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@EnableTransactionManagement
public class SpringMybatisConfig {

    @Value("${jdbc.driverClassName}")
    private  String jdbcDriver;
    @Value("${jdbc.url}")
    private  String jdbcUrl;
    @Value("${jdbc.userName}")
    private  String jdbcUserName;
    @Value("${jdbc.password}")
    private  String jdbcPassword;
    /**
     * 初始化大小
     * ，最小，最大
     */

    @Value("${jdbc.initialSize}")
    private  int initialSize;

    @Value("${jdbc.minIdle}")
    private  int minIdle;

    @Value("${jdbc.maxActive}")
    public  int maxActive;
    /**
     * 配置获取连接等待超时的时间
     */
    @Value("${jdbc.maxWait}")
    private  long maxWait;
    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    @Value("${jdbc.timeBetweenEvictionRunsMillis}")
    private  long timeBetweenEvictionRunsMillis;
    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("${jdbc.minEvictableIdleTimeMillis}")
    private  long minEvictableIdleTimeMillis;

    @Value("${jdbc.typeAliasesPackage}")
    private  String typeAliasesPackage;

    @Value("${mybatis.mapperLocations}")
    private  String mapperLocations;

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

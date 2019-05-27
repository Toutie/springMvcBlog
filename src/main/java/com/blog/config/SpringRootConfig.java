package com.blog.config;

import org.springframework.context.annotation.Configuration;

/**配置系统初始化参数
 * @author wang
 */
@Configuration
public class SpringRootConfig {
    /**
     * 系统版本号
     */
    public static final String VERSION ="";
    /**
     * 配置下系统URL
     */
    public static String api_url ="";

    /**
     * 基础包配置
     */
    public static final String BASE_PACKAGE = "com.blog";
    /**
     * 默认请求编码
     */
    public static final String DEFAULT_ENCODING = "UTF-8";



}

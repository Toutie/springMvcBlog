package com.blog.config;


import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**框架初始化
 * @author wang
 */

public class WebInitialization extends AbstractAnnotationConfigDispatcherServletInitializer{


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringMybatisConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    /**
     * 配置过滤器
     *
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
    //post编码过滤器

         CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
         encodingFilter.setEncoding("UTF-8");
         encodingFilter.setForceEncoding(true);

         return new Filter[]{encodingFilter};
    }
}

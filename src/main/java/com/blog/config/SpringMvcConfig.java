package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;


/**
 * @author wang
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.blog"})
public class SpringMvcConfig implements WebMvcConfigurer{
    /**
     * 静态资源拦截
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
        .addResourceLocations("/WEB-INF/static/");

    }

    /**
     * 模板资源解析器
     * @return
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //开发时为false
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    /**
     * 模板引擎
     * @param templateResolver
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    /**
     *视图解析器
     * @param templateEngine
     * @return
     */
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();

        thymeleafViewResolver.setTemplateEngine(templateEngine);

        thymeleafViewResolver.setCharacterEncoding("UTF-8");

        return thymeleafViewResolver;
    }

    /**
     * 配置個攔截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor())
                .addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}

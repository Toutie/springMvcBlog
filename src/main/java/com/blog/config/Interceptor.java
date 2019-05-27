//package com.blog.config;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
///**作为伪拦截器使用
// * @author wang
// */
//
//public class Interceptor extends AbstractAnnotationConfigDispatcherServletInitializer{
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{SpringMybatisConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[]{SpringMvcConfig.class};
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//}

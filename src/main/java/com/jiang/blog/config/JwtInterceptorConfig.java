package com.jiang.blog.config;

import com.jiang.blog.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自己的拦截器,并设置拦截的请求路径
        //addPathPatterns为拦截此请求路径的请求
        //excludePathPatterns为不拦截此路径的请求
        //要拦截user下的所有访问请求，
        String[] addPathPatterns = {
                "/v1/user/*"
        };
        //需要排除的路径，不能拦截此请求
        String[] excludePathPatterns = {
                "/**"
        };
        registry
                .addInterceptor(new JwtInterceptor())
                .addPathPatterns(addPathPatterns)
                .excludePathPatterns(excludePathPatterns);
    }
}

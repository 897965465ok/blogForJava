package com.jiang.blog.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述：     配置地址映射
 */
@Configuration
public class BlogWebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/admin/");

        /* registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");*/

//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:" + Constant.FILE_UPLOAD_DIR);
//        registry
//                .addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
      /*  registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");*/

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
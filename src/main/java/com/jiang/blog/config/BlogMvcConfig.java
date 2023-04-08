package com.jiang.blog.config;


import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述：     配置地址映射
 */
@Configuration
public class BlogMvcConfig implements WebMvcConfigurer {


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

    // CORS
 /*   @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {

            SaRouter.match("/v1/articles").check(r ->StpUtil.checkPermission("article:list"));
            SaRouter.match("/v1/article").check(r -> StpUtil.checkPermission("article:plus"));

/*            SaRouter.match(SaHttpMethod.PUT).match("/v1/article").check(r -> StpUtil.checkPermission("article:edit"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/article").check(r -> StpUtil.checkPermission("article:query"));
            SaRouter.match(SaHttpMethod.DELETE).match("/v1/article").check(r -> StpUtil.checkPermission("article:delete"));*/
        })).addPathPatterns("/**");
    }

}
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

            /* 文章拦截*/
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryManyArticle").check(r -> StpUtil.checkPermission("article:list"));
            SaRouter.match(SaHttpMethod.DELETE).match("/v1/deleteArticle").check(r -> StpUtil.checkPermission("article:delete"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryOneArticle").check(r -> StpUtil.checkPermission("article:query"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryArticleTableHeader").check(r -> StpUtil.checkPermission("article:query"));


/*
            SaRouter.match(SaHttpMethod.PUT).match("/v1/article").check(r -> StpUtil.checkPermission("article:plus"));

            SaRouter.match(SaHttpMethod.PUT).match("/v1/article").check(r -> StpUtil.checkPermission("article:edit"));*/

            /*  菜单拦截*/

            SaRouter.match(SaHttpMethod.POST).match("/v1/createMenu").check(r -> StpUtil.checkPermission("menu:plus"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/deleteMenu").check(r -> StpUtil.checkPermission("menu:delete"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/deleteManyMenu").check(r -> StpUtil.checkPermission("menu:delete"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/menus").check(r -> StpUtil.checkPermission("menu:query"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryMenuTableHeader").check(r -> StpUtil.checkPermission("menu:query"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/menu").check(r -> StpUtil.checkPermission("menu:query"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/updateMenuById").check(r -> StpUtil.checkPermission("menu:edit"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/getRouter").check(r -> StpUtil.checkPermission("menu:query"));

            /* 角色拦截*/

            SaRouter.match(SaHttpMethod.GET).match("/v1/queryManyRole").check(r -> StpUtil.checkPermission("role:query"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryRoleTableHeader").check(r -> StpUtil.checkPermission("role:query"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/createRole").check(r -> StpUtil.checkPermission("role:plus"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/changeRole").check(r -> StpUtil.checkPermission("role:edit"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/deleteManyRole").check(r -> StpUtil.checkPermission("role:delete"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/getAllRoles").check(r -> StpUtil.checkPermission("role:query"));

            /*标签拦截*/
            SaRouter.match(SaHttpMethod.GET).match("/v1/tags").check(r -> StpUtil.checkPermission("tags:query"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/tags").check(r -> StpUtil.checkPermission("tags:plus"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/query").check(r -> StpUtil.checkPermission("tags:query"));
            SaRouter.match(SaHttpMethod.DELETE).match("/v1/tags").check(r -> StpUtil.checkPermission("tags:delete"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/update").check(r -> StpUtil.checkPermission("tags:edit"));

            /*用户管理*/
            SaRouter.match(SaHttpMethod.POST).match("/v1/userUpdate").check(r -> StpUtil.checkPermission("user:edit"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/deleteManyUser").check(r -> StpUtil.checkPermission("user:delete"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryUserTableHeader").check(r -> StpUtil.checkPermission("user:delete"));
            SaRouter.match(SaHttpMethod.GET).match("/v1/queryManyUser").check(r -> StpUtil.checkPermission("user:delete"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/queryRolesByUserId").check(r -> StpUtil.checkPermission("tags:query"));
            SaRouter.match(SaHttpMethod.POST).match("/v1/getInfo").check(r -> StpUtil.checkPermission("tags:query"));


        })).addPathPatterns("/**");
    }

}